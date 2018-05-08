<?php 

	define('BD_USUARIO', 'ssiipicto_bbdd');	
	define('RAIZ', 'http://localhost/ssiipicto_bbdd');
	define('BD_HOST', 'localhost');
	define('BD_USER', 'hypatia');
	define('BD_PASS', 'hypatia');
	define('INSTALADA', true);
	define('SIZE_PAGE',4);
	define('SIZE_PAGE_FAV',8);
	define('SIZE_PAGE_FAV1',6);
	define('DIR_ALMACEN', __DIR__.'/../img/');
	
	$EXTENSIONES_PERMITIDAS = array('gif','jpg','jpe','jpeg','png');
	
	register_shutdown_function('cierraConexion');
	
	
	$BD = new mysqli(BD_HOST, BD_USER,BD_PASS,BD_USUARIO);
		if (mysqli_connect_errno() ) {
			echo "Error de conexión a la BD: ".mysqli_connect_error();
			exit();
		}
	if ( ! $BD->set_charset("utf8mb4")) {
		echo "Error al configurar la codificación de la BD: (" . $BD->errno . ") " . utf8_encode($BD->error);
		exit();
	}
	
	
	function cierraConexion() {
	  // Sólo hacer uso de global para cerrar la conexion !!
	  global $BD;
	  if ( isset($BD) && ! $BD->connect_errno ) {
		$BD->close();
	  }
	}

session_start();	

?>