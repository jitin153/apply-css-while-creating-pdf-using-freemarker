<html>
	<body>
	<table>
		<tr>
			<th>ROLL NUMBER</th>
			<th>NAME</th>
			<th>DATE OF BIRTH</th>
			<th>PERCENTAGE</th>
			<th>RESULT</th>
		</tr>
			<#if data??>
				<#list data as item>
					<#if item??>
						<tr>
							<td>${item.rollNumber}</td>
							<td>${item.name}</td>
							<td>${item.dateOfBirth?string("dd-MMM-yyyy")}</td>
							<td>${item.percentage}%</td>
							<td><#if item.isPass == true>PASS<#else>FAIL</#if></td>
						</tr>
					</#if>
				</#list>
			</#if>
		</table>
	</body>
</html>