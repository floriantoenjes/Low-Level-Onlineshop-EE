<%@ include file="header.jspf" %>
<article>
	<section>
		<form action="search" method="post">
		<fieldset>
		<legend>Suchen</legend>
		<table>
			<tbody>
			<tr>
				<th>
					<label 
					for="search">Suche:</label>
				</th>
				<td>
					<input 
						type="text" 
						name="search"
						size="40"
						maxlength="40"
						title="Suchtext"
						placeholder=
						"Suchtext eingeben"
						>
				</td>
				<td>
					<input type="submit"> 		
					<input type="reset"> 		

				</td>
			</tr>
		</tbody>
		</table>
		</fieldset>
		</form>
	</section>
</article>


<%@ include file="footer.jspf" %>
