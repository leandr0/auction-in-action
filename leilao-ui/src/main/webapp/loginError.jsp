<html>
	<head>
		<title>Auction in Action</title>
	</head>
	<body>
		<form method="POST" action="j_security_check">
			<div style="padding: 15% 5% 5% 40%;width: 250px;">
				<div style="background : #F5F5F5;">
				<fieldset>
					<legend>Dados de acesso:</legend>
						<table>
							<tr>
								<td>Login:</td>
								<td>
									<input type="text" name="j_username"/>
								</td>
							</tr>
							<tr>
								<td>Senha:</td>
								<td>
									<input type="password" name="j_password"/>
								</td>
							</tr>
						</table>
						<div align="center">
							<h5 style="color:red">Login e/ou senha inválida.</h5>
							<input type="submit" value="Login"/>
						</div>
				</fieldset>
			</div>
			</div>
		</form>
	</body>
</html>