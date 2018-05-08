<?php
	
	require_once __DIR__.'/config.php';

	$numElements = isset($_POST['numElementos']) ? $_POST['numElementos'] : null ;
	$numColumns = isset($_POST['numColumnas']) ? $_POST['numColumnas'] : null ;
	$sizeBox = isset($_POST['box']) ? $_POST['box'] : null ;
	$sizeTextBox = isset($_POST['textBox']) ? $_POST['textBox'] : null ;
	$top = isset($_POST['top']) ? $_POST['top'] : null ;
	$bottom = isset($_POST['bottom']) ? $_POST['bottom'] : null ;
	$click = isset($_POST['click']) ? $_POST['click'] : null ;
	$refresh = isset($_POST['refresh']) ? $_POST['refresh'] : null ;
	$filter = isset($_POST['filter']) ? $_POST['filter'] : null ;

	if(isset($_POST['save']) && !empty($_POST['save'])){
		$_SESSION['elementosGuardados'] = $_POST['save'];
		//$_SESSION['endRefresh'] = true;
		//echo '<pre>' . print_r($_SESSION, TRUE) . '</pre>';
	}
	if(isset($_POST['remove']) && !empty($_POST['remove'])){
		$_SESSION['numElementos'] = $_SESSION['numElementos']-1;
	}

	if(isset($_POST['getElements']) && !empty($_POST['getElements'])){
		echo $_SESSION['numElementos'];
	}

	if(isset($_POST['getColumns']) && !empty($_POST['getColumns'])){
		echo $_SESSION['numColumnas'];
	}

	if(isset($_POST['getSaveElements']) && !empty($_POST['getSaveElements'])){
		echo json_encode($_SESSION['elementosGuardados']);
	}

	if($click){
		$_SESSION['numElementos'] = $numElements;
		$_SESSION['numColumnas'] = $numColumns;
		//echo '<pre>' . print_r($_SESSION, TRUE) . '</pre>';
	}

	if(isset($_POST['index']) && !empty($_POST['index'])) {
		createBox($_POST['index'],"./blanco.png");
		$_SESSION['numElementos'] = $_SESSION['numElementos']+1;
		echo '<script> 
	    			$(document).ready(function(){
	    				$(\'#box-S\').insertAfter(\'#box-\'+ '.$_POST['index'].');
	    				$(\'.image\').css({"filter":"'.$filter.'"});
	    				$(\'.box-grid-container\').css({"width":"'.$sizeBox.'px"});
	    				$(\'.box-grid-container\').css({"height":"'.$sizeBox.'px"});
	    				$(\'.clean-remove-buttons\').css({"width":"'.$sizeBox.'px"});
	    				$(\'.clean-remove-buttons\').css({"height":"'.$sizeBox.'px"});
	    				$(\'.box-text-container-top\').css({"width":"'.$sizeTextBox.'px"});
	    				$(\'.box-text-container-bottom\').css({"width":"'.$sizeTextBox.'px"});
	    				$(\'.box-text-container-top\').css({"display":"'.$top.'"});
    					$(\'.box-text-container-bottom\').css({"display":"'.$bottom.'"});
    					$(\'.box-grid-container\').droppable({
							accept : \'#draggable,.translator-image\', 
							drop: function( event, ui ) {
								dropEvent($(this),ui);
							}
						});
	    			});
				</script>';
	}
	if ($numElements) {
	    echo "<div class=\"grid-container-editor\">";
	    $aux = "";
	    if($numColumns){
	    	for ($i = 1; $i <= $numColumns; $i++) {
    			$aux = $aux . " auto";
			}
	    	echo '<script> 
	    			$(document).ready(function(){
	    				$(\'.grid-container-editor\').css({"grid-template-columns":"'.$aux.'"});
	    				$(\'.box-grid-container\').css({"width":"'.$sizeBox.'px"});
	    				$(\'.box-grid-container\').css({"height":"'.$sizeBox.'px"});
	    				$(\'.clean-remove-buttons\').css({"width":"'.$sizeBox.'px"});
	    				$(\'.clean-remove-buttons\').css({"height":"'.$sizeBox.'px"});
	    				$(\'.box-text-container-top\').css({"width":"'.$sizeTextBox.'px"});
	    				$(\'.box-text-container-bottom\').css({"width":"'.$sizeTextBox.'px"});
	    				$(\'#box-S\').click(function(){
						    addNewBox('.$_SESSION['numColumnas'].');
						  });
						$(\'.box-grid-container\').droppable({
							accept : \'#draggable,.translator-image\', 
							drop: function( event, ui ) {
								dropEvent($(this),ui);
								wait();
							}
						});
	    			});
	    		</script>';
	    }

	    $indice = 0;
	    $arrayElems = count($_SESSION['elementosGuardados']);
	    //print_r($_SESSION['elementosGuardados']);
		if($refresh==1){
			//echo "<div>".$arrayElems."</div>";
			for ($i = 1; $i <= $numElements; $i++) {
				//$id = "image-".$i;
				if($indice < $arrayElems && $_SESSION['elementosGuardados'][$indice]['idPadre']==$i){
					//createBox($i,$_SESSION['elementosGuardados'][$indice]['url']);
					echo "<div id=\"box-".$i."\" class=\"box\">";
			    	echo "<div id=\"box-text-top-".$i."\" class=\"box-text-container-top\"><textarea class=\"form-control box-text\" rows=\"1\">".$_SESSION['elementosGuardados'][$indice]['valueTopText']."</textarea></div> ";
			        echo "<div id=\"box-image".$i."\" class=\"box-grid-container\">";
			        echo "<img id=\"image-".$i."\" class=\"box-grid-container-image image\" src=\"".$_SESSION['elementosGuardados'][$indice]['url']."\"></img>";
			        echo "<div id=\"clean-remove-".$i."\" class=\"clean-remove-buttons\">";
			        echo "<button type=\"button\" class=\"btn btn-clear\" onclick=\"clean(this)\"></button>";
			        echo "<button type=\"button\" class=\"btn btn-delete\" onclick=\"remove(this)\"></button>";
			        echo "</div>";
			        echo "</div>";
			        echo "<div id=\"box-text-bottom-".$i."\" class=\"box-text-container-bottom\"><textarea class=\"form-control box-text\" rows=\"1\">".$_SESSION['elementosGuardados'][$indice]['valueBottomText']."</textarea></div> ";	
			        echo "</div>";
					$indice = $indice+1;
				}else{
					createBox($i,"./blanco.png");
				}
			}
		}else{

			for ($i = 1; $i <= $numElements; $i++) {
            	createBox($i,"./blanco.png");
        	}
        }

    	echo "<div id=\"box-S\" class=\"box-grid-container box-add-box\">";
        //echo "<img id=\"box-S\" class = \"box-image-box\" src= \"/TFM/add.png\"></img>";	
        echo "</div>";
        echo "</div>";
 
	}

	function createBox($index,$url){
		echo "<div id=\"box-".$index."\" class=\"box\">";
    	echo "<div id=\"box-text-top-".$index."\" class=\"box-text-container-top\"><textarea class=\"form-control box-text\" rows=\"1\"></textarea></div> ";
        //echo "<div id=\"box-image".$index."\" class = \"box-grid-container\" ondragenter=\"dragenter(event,this)\" ondrop=\"drop(event,this)\" ondragover=\"dragover(event)\" ondragleave=\"dragleave(event,this)\"></div>";
        echo "<div id=\"box-image".$index."\" class=\"box-grid-container\">";
        echo "<img id=\"image-".$index."\" class=\"box-grid-container-image image\" src=\"".$url."\" draggable=\"true\"></img>";
        echo "<div id=\"clean-remove-".$index."\" class=\"clean-remove-buttons\">";
        echo "<button type=\"button\" class=\"btn btn-clear\" onclick=\"clean(this)\"></button>";
        echo "<button type=\"button\" class=\"btn btn-delete\" onclick=\"remove(this)\"></button>";
        echo "</div>";
        echo "</div>";
        echo "<div id=\"box-text-bottom-".$index."\" class=\"box-text-container-bottom\"><textarea class=\"form-control box-text\" rows=\"1\"></textarea></div> ";	
        echo "</div>";
        
	}

?>