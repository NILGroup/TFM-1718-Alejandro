<?php
	require_once __DIR__.'/BD.php';

	function searchPictograms($word){
		return getPictograms($word);
	}
?>