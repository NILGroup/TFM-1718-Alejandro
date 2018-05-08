<?php
	require_once __DIR__.'/config.php';
	require_once __DIR__.'/logica.php';

	header('Content-type: text/html; charset=UTF-8');

	$frase = isset($_POST['texto']) ? $_POST['texto'] : null ;

	if(!empty($frase)){

		$frase_aux = utf8_decode ($frase);

		$frase_aux = str_replace(" ", "%20", $frase_aux);

		$url = 'http://hypatia.fdi.ucm.es:5223/PICTAR/traducir/'.$frase_aux;

		$respuesta = file_get_contents($url);

		$respuesta = json_decode($respuesta);


		$sizeBox = isset($_POST['box']) ? $_POST['box'] : null ;
		$heightBox = isset($_POST['heightBox']) ? $_POST['heightBox'] : null ;

		$i = 0;
		echo "<div class=\"grid-container-translator\">";
		foreach ($respuesta as $palabra) { 
			$_SESSION['urlpal'.$i] = obtenerUrls($palabra);
			if(count($_SESSION['urlpal'.$i])>1){
				$aux = true;
			}else{
				$aux = false;
			}
			$urls = implode(",",$_SESSION['urlpal'.$i]);
			echo "<div id=\"translator-box-".$i."\">";
			if($aux){
				echo "<button id=\"translator-button-up-".$i."\" type=\"button\" class=\"glyphicon glyphicon-chevron-up translator-button-up\" onclick=\"passImageUp(this,'".$urls."')\"></button>";
			}else{
				echo "<button id=\"translator-button-up-".$i."\" type=\"button\" class=\"glyphicon glyphicon-chevron-up translator-button-up-aux\"></button>";
	        }
	        echo "<div id= \"div".$i."\">";
	        if ($_SESSION['urlpal'.$i][0] == "error"){
	        	$url_pic = './error.png';
	        }else{
	        	$url_pic = 'http://hypatia.fdi.ucm.es/conversor/Pictos/'.$_SESSION['urlpal'.$i][0];
	        }
	        //$url = $_SESSION['urlpal'.$i][0];
	        //print_r($_SESSION['urlpal'.$i]);
	        echo "<img id = \"translator-image-".$i."\" class= \"translator-image\" src =\"$url_pic\" class=\"imagen\" draggable=\"true\">";
			echo "</div>";
			if($aux){
				echo "<button id=\"translator-button-down-".$i."\" type=\"button\" class=\"glyphicon glyphicon-chevron-down translator-button-down\" onclick=\"passImageDown(this,'".$urls."')\"></button>";
			}else{
				echo "<button id=\"translator-button-down-".$i."\" type=\"button\" class=\"glyphicon glyphicon-chevron-down translator-button-down-aux\"></button>";
			}
			echo "</div>";
	        $i = $i + 1;
		}
		echo "<div/>";

		echo '<script>      
				$(document).ready(function(){
					$(\'.translator-image\').css({"width":"'.$sizeBox.'px"});
		    		$(\'.translator-image\').css({"height":"'.$heightBox.'px"});
					$(\'.translator-image\').draggable({
		    			helper:\'clone\',
		    			stack:\'.translator-image\',
		    			appendTo: \'body\'
		    		});
				});
				</script>';
	}else{
		echo "empty";
	}
	 //print_r($_SESSION, TRUE);

	//0 aux[5581, 5858] será
    /*$respuesta_aux = str_replace(" ", "",$respuesta[1]);
	$respuesta_aux = explode("[", $respuesta_aux);
	$respuesta_aux = explode("]", $respuesta_aux[1]);
	$urls = explode(",", $respuesta_aux[0]);
	print_r($urls);*/

	//echo "<div>".$_SESSION['urlpal0'][0]."</div>";
	//echo '<pre>' . print_r($_SESSION, TRUE) . '</pre>';

	function obtenerUrls($palabra){
		$respuesta = str_replace(" ", "",$palabra);
		$respuesta_aux = explode("[", $respuesta);
		if(count($respuesta_aux)==1){
			$result[0] = "error";
			return $result;
		}else{
			$respuesta_aux = explode("]", $respuesta_aux[1]);
			$urls = explode(",", $respuesta_aux[0]);
			for ($j = 0; $j < count($urls); $j++) {
				$k = 0;
				$encontrado = false;
				while(($k < $j) && (!$encontrado)){
					if(isset($result[$k])){
						if($urls[$j] == $result[$k]){
							$encontrado = true; 
						}
					}
					$k++;
				}
				if(!$encontrado){
					$result[$j] = $urls[$j];
				}
			}
			return $result;
		}
	}

	
?>