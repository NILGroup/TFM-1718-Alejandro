<?php
	require_once __DIR__.'/config.php';
	require_once __DIR__.'/logica.php';

	$busqueda = isset($_POST['busqueda']) ? $_POST['busqueda'] : null ;
	$sizeBox = isset($_POST['box']) ? $_POST['box'] : null ;
	$heightBox = isset($_POST['heightBox']) ? $_POST['heightBox'] : null ;
	$resultado = null;
	if($busqueda){
		$resultado = searchPictograms($busqueda);
	}
	if($resultado){

		echo '<script> 
	    			$(document).ready(function(){
	    				$(\'.seeker-image\').css({"width":"'.$sizeBox.'px"});
	    				$(\'.seeker-image\').css({"height":"'.$heightBox.'px"});
	    				$(\'.seeker-image\').draggable({
	    					helper:\'clone\',
	    					stack:\'.seeker-image\',
		    				appendTo: \'body\'
	    				});
	    			});
	    		</script>';

		$i = 1;
		echo "<div class=\"grid-container-seeker scroll\">";
    	foreach ($resultado as $registro) { 
	        echo "<div id= \"div".$i."\">";
	        //echo "<img id = \"draggable\" class= \"seeker-image\" src =".$registro['url']." class=\"imagen\" draggable=\"true\" ondragstart=\"dragstart(event,this)\">";
	        echo "<img id = \"draggable\" class= \"seeker-image\" src =".$registro['url']." class=\"imagen\" draggable=\"true\">";
	        echo "</div>";
	        $i = $i + 1;
    	}
    	echo "<div/>";
	}else{
		if($busqueda){
			echo "<div class=\"alert alert-warning\"><strong>¡Atención!</strong> La palabra o frase que está buscando no se encuentra en la base de datos.</div>";
		}
	}
	
?> 