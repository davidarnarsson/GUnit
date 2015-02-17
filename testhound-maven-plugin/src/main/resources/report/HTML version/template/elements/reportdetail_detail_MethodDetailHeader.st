<div id="$UsedFieldsListID$link"></div>
<div id="$testcasename$link"></div>
<tr BGCOLOR="#CCCCFF" CLASS="TableSubHeadingColor_TestDetails">
	<TH ALIGN="left" COLSPAN="3"><FONT SIZE="+2">$testcasename$</FONT></th>
	<tr>
	<td><b>Used Fields</b> 
		<a href="#$UsedFieldsListID$link" onclick="toggle2('$UsedFieldsListID$', this)">hide</a>
	</td>
	<td><b>Delegate Fields</b> 
		<a href="#$UsedFieldsListID$link" onclick="toggle2('$DelegateFieldsListID$', this)">hide</a>
	</td>
	<td><b>Unused Fields</b>
		<a href="#$UsedFieldsListID$link" onclick="toggle2('$UnUsedFieldsListID$', this)">show</a>
	</td>
</tr>
<tr>
	<td>
		<div id="$UsedFieldsListID$" style="display:''">
		$UsedFieldsList$
		</div>
	</td>
		<td>
		<div id="$DelegateFieldsListID$" style="display:''">
		$DelegateFieldsList$
		</div>
	</td>
	<td>
		<div id="$UnUsedFieldsListID$" style="display:none">
		$UnUsedFieldsList$
		</div>
	</td>
</tr>
<tr>
<TH ALIGN="left" COLSPAN="3">Invoked Helper Methods</TH>
</tr>
<tr>
<TD ALIGN="left" COLSPAN="3">$invokedHelperMethods$</TD>
</tr>
</tr>
