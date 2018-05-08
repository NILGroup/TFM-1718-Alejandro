<?php
	
	require_once __DIR__.'/config.php';

    $importResult = isset($_POST['import_result']) ? $_POST['import_result'] : null ;
    $result = json_decode($importResult);

    $_SESSION['elementosGuardados'] = objectToArray($result);

    function objectToArray($d) 
    {
        if (is_object($d)) {
            // Gets the properties of the given object
            // with get_object_vars function
            $d = get_object_vars($d);
        }

        if (is_array($d)) {
            /*
            * Return array converted to object
            */
            return array_map(__FUNCTION__, $d);
        } else {
            // Return array
            return $d;
        }
    }

    print_r($_SESSION['elementosGuardados']);
    
	/*$fichero = '/tmp/pictar.txt';
	$contenido = json_encode($_SESSION['elementosGuardados']);
	file_put_contents($fichero, $contenido);*/

	//echo "<div display='none'><script type='text/javascript'>console.log('console log message')</script></div>";


    /*header('Content-Description: File Transfer');
    header('Content-Type: application/octet-stream');
    header('Content-Disposition: attachment; filename="'.basename($fichero).'"');
    header('Expires: 0');
    header('Cache-Control: must-revalidate');
    header('Pragma: public');
    header('Content-Length: ' . filesize($fichero));
    readfile($fichero);*/


?>