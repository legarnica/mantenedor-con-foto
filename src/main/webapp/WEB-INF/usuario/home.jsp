<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mantenedor de usuarios</title>
<!-- bootstrap -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<!-- bootstrap # -->

<!-- css - data table -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/v/dt/dt-1.10.21/datatables.min.css" />
<!-- css - data table # -->

</head>
<body>

	<div class="container">
		<div class="row my-5">
			<div class="col-2"></div>
			<div class="col-8">
				<!-- Formulario -->
				<div>

					<c:if test="${not empty mensaje}">
						<div class="alert alert-warning alert-dismissible fade show"
							role="alert">
							<strong>Información</strong> ${mensaje}
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
					</c:if>


					<h2>Mantenedor de Usuarios</h2>
					<form id="formulario" action="<c:url value='/usuarios'/>" method="post"
						enctype="multipart/form-data">
						
						<div class="form-group">
							<label for="marca">Nombre</label> <input id="nombre" name="nombre"
								type="text" class="form-control" />
						</div>

						<div class="form-group">
							<label for="modelo">Correo</label> <input id="correo"
								name="correo" type="email" class="form-control" />
						</div>

						<div class="form-group">
							<label for="anio">Contraseña</label> <input id="contrasenia" name="contrasenia"
								type="password" class="form-control" required="required" />
						</div>

						<div class="form-group">
							<label for="precio">Imágen</label> <input id="imagen"
								name="imagen" type="file" class="form-control" />
						</div>

						<button id="boton" type="submit" class="btn btn-primary">Guardar</button>
					</form>
				</div>

				<!-- Formulario # -->

				<!-- TABLA DE AUTOMOVIL -->
					<h2>Lista de usuarios</h2>
					<table id="tablaAutos" class="table">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">nombre</th>
								<th scope="col">correo</th>
								<th scope="col">contrasenia</th>
								<th scope="col">Imagen</th>
								<th scope="col">Acción</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th scope="col">#</th>
								<th scope="col">nombre</th>
								<th scope="col">correo</th>
								<th scope="col">contrasenia</th>
								<th scope="col">Imagen</th>
								<th scope="col">Acción</th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach var="usuario" items="${usuarios}">
								<tr>
									<th scope="row">${usuario.id}</th>
									<td>${usuario.nombre}</td>
									<td>${usuario.correo}</td>
									<td>${usuario.contrasenia}</td>
									<td><img width="100" height="100" src="<c:url value='${usuario.urlImagen}'></c:url>" class="rounded" alt="${usuario.urlImagen}" /></td>
									<td>
									<a href='javascript:actualizar(${usuario.toJson()})'>Actualizar</a> | 
									<a href='javascript:eliminar(${usuario.toJson()})'>Eliminar</a>
									
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				<!-- TABLA DE AUTOMOVIL # -->
			</div>
			<div class="col-2"></div>
		</div>
	</div>

	<!-- bootstrap -->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
	<!-- bootstrap # -->

	<!-- javascript - data table -->
	<script type="text/javascript"
		src="https://cdn.datatables.net/v/dt/dt-1.10.21/datatables.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#tablaAutos').DataTable();
		});
	</script>
	<!-- javascript - # data table -->
	
	<script type="text/javascript">
		const eliminar = (usuario) => {
			if(!confirm("seguro que desea eliminar usuario id: " + usuario.correo))
				return

			const baseUrl = window.location.origin
			window.location.href = baseUrl + '/usuarios/eliminar?id=' + usuario.id
		}
		
		const actualizar = (usuario) => {
			// capturamos el formulario
			const formulario = document.querySelector('#formulario')
			formulario.nombre.value = usuario.nombre
			formulario.correo.value = usuario.correo

			// eliminamos los imputs si existen, si existe
			// será capturado por su id
			const input001 = formulario.elementoGenerado001
			if(input001)
				input001.remove();

			const input002 = formulario.elementoGenerado002
			if(input002)
				input002.remove();

			// creamos dos input escondidos, con el resto
			// de los atributos que necesitaremos para
			// la actualización
			const input_id = document.createElement('input')
			input_id.type = 'hidden'
			input_id.name = 'id'
			input_id.value = usuario.id
			input_id.id = 'elementoGenerado001'

			const input_url_imagen = document.createElement('input')
			input_url_imagen.type = 'hidden'
			input_url_imagen.name = 'urlImagen'
			input_url_imagen.value = usuario.urlImagen
			input_url_imagen.id = 'elementoGenerado002'

			// agregamos estos campos creados al formulario
			formulario.appendChild(input_id);
			formulario.appendChild(input_url_imagen);
			// adaptamos el botón a una actualización
			// lo capturamos por su id
			formulario.boton.textContent = 'Actualizar'
			// cambiamos el método para que vaya a actualizar
			formulario.action = '/usuarios/actualizar'
		}

	</script>

</body>
</html>