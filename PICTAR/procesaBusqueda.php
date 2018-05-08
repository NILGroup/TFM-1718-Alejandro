<?php
	require_once __DIR__.'/config.php';

	if(isset($_POST['busqueda'])){
		$busqueda= $_POST['busqueda'];
	}
	$busqueda = "Location:prueba1.php?search=".$busqueda;
	header($busqueda);
	return $busqueda; 
?> 