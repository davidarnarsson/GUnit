package edu.chl.gunit.intellij;


import com.google.common.collect.ImmutableSortedMap;
import edu.chl.gunit.intellij.pollers.MessagePoller;
import edu.chl.gunit.intellij.recipients.MessageRecipient;
import edu.chl.gunit.service.client.Client;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ivar on 04/03/15.
 */
public class ServiceListener implements Runnable {

    private class Poller {
        public MessagePoller poller;
        public long queueTime;

        public Poller(MessagePoller poller, long pollDate) {
            this.poller = poller;
            this.queueTime = pollDate;
        }

        public Poller(MessagePoller poller) {
            this.poller = poller;
            this.queueTime = new Date().getTime() + poller.getInterval() * 1000l;
        }
    }

    private final static Object LOCKER = new Object();

    private final Client client;

    private List<Poller> poll = new ArrayList<Poller>();
    private Map<String, List<MessageRecipient>> recipients = new HashMap<String, List<MessageRecipient>>();

    public ServiceListener(Client client) {
        this.client = client;
    }

    public void addRecipient(MessageRecipient recipent) {
        synchronized (LOCKER) {
            if (!this.recipients.containsKey(recipent.forMessageName())) {
                this.recipients.put(recipent.forMessageName(), new ArrayList<MessageRecipient>());
            }

            this.recipients.get(recipent.forMessageName()).add(recipent);
        }
    }

    public void addPoller(MessagePoller poller) {
        synchronized (LOCKER) {
            this.poll.add(new Poller(poller));
        }
    }

    @Override
    public void run() {
        while (true) {
            Poller poller = getLatestPoller();

            if (poller != null) {
                doPoll(poller.poller);
            }

            long sleep = calculateSleepPeriod();
            try {
                Thread.sleep(Math.max(10, sleep));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sortPollers() {
        Collections.sort(poll, new Comparator<Poller>() {
            @Override
            public int compare(Poller o1, Poller o2) {
                return new Long(o1.queueTime).compareTo(o2.queueTime);
            }
        });
    }

    private Poller getLatestPoller() {
        if (poll.size() == 0) return null;

        sortPollers();

        return poll.remove(0);
    }

    private long calculateSleepPeriod() {
        if ( poll.size() == 0) return 1000;

        sortPollers();

        return poll.get(0).queueTime - new Date().getTime();
    }

    private <T> void doPoll(MessagePoller<T> poller) {
        try {
            T result = poller.poll(this.client);

            if (result != null && recipients.containsKey(poller.getMessageName())) {
                List<MessageRecipient> r = recipients.get(poller.getMessageName());
                List<MessageRecipient> toRemove = new ArrayList<MessageRecipient>();


                for (MessageRecipient<T> m : r) {
                    m.receive(result);

                    if (m.oneTime()) {
                        toRemove.add(m);
                    }
                }

                for (MessageRecipient tr : toRemove) {
                    recipients.get(poller.getMessageName()).remove(tr);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getCanonicalName()).log(Level.SEVERE, "Unable to perform poll with poller " + poller.getClass().getCanonicalName());
        }
        finally {
            if (!poller.oneTime()) {
                addPoller(poller);
            }
        }
    }
}
