<?php

function getPictograms($word){
	global $BD;
	$ok = false;
	$ResultArray = array();
	//$query = "SELECT url FROM palabras,pictogramas where palabras.nombre = '".$BD->real_escape_string($word)."' and palabras.id_url = pictogramas.id_pictograma";
	$query = "SELECT DISTINCT url FROM palabras,pictogramas where palabras.nombre LIKE '".$BD->real_escape_string($word)."%' and palabras.id_url = pictogramas.id_pictograma";
	$resultado = $BD->query($query);
	if ($resultado) {
		$ok = true;
	}
	while($fila = $resultado->fetch_assoc()){
		$ResultArray[] = $fila;
	}
	if($ok){
		$resultado->close();
		return $ResultArray;
	}
	else{
		return false;
	}	
}
?>