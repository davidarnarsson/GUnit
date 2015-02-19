CREATE TABLE JaCoCoResult(

	className VARCHAR(64),
	groupName VARCHAR(64),
	packageName VARCHAR(64),

	 instructionMissed int,
	 instructionCovered int,
     branchMissed int,
     branchCovered int,
     lineMissed int,
     lineCovered int,
     complexityMissed int,
     complexityCovered int,
     methodMissed int,
     methodCovered int
);

CREATE TABLE TestCase(

	succeeded bit,
	error VARCHAR(100),
	stackTrace VARCHAR(1000),
	errorType VARCHAR(64),
	className VARCHAR(64),
	timeElapsed VARCHAR(64),
	timeOfTest timestamp,
	userid int

);

CREATE TABLE TestSuiteResults(

	timeElapsed float,
	tests int,
	errors int,
	skipped int,
	failures int,
	userid int
);