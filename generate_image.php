<?php

if($_SERVER['REMOTE_ADDR'] == "127.0.0.1")
{
	//Connect to local DB
}
else{
	//Connect to remote DB
}

$img_width = 204;
$img_height = 204;

$img = imagecreate($img_width, $img_height);
$bg_color = imagecolorallocate($img, 0, 0, 0);
$line_color = imagecolorallocate($img, 0, 255, 0);
$text_color = imagecolorallocate($img, 255, 0, 0);
$divider_color = imagecolorallocate($img, 50, 50, 50);

//Print graph for last 24 hours
PrintGraph($img, $img_width * 0.02, $img_height * 0.01, $img_width * 0.96, $img_height * 0.48, 24, "/60/60");
//Print graph for last 14 days
PrintGraph($img, $img_width * 0.02, $img_height * 0.51, $img_width * 0.96, $img_height * 0.48, 14, "/60/60/24");

imagestring($img, 3, 4, $img_height * 0.5 - 12, "LAST 24 HOURS", $text_color);
imagestring($img, 3, 4, $img_height - 12, "LAST 14 DAYS", $text_color);
imageline($img, 0, $img_height * 0.5, $img_width, $img_height * 0.5, $divider_color);

header("Content-type: image/png");
imagepng($img);

function PrintGraph($img, $start_x, $start_y, $width, $height, $item_count, $seconds_divider)
{
	global $line_color, $text_color, $divider_color;
	$end_x = $start_x + $width;
	$end_y = $start_y + $height;
	$counts = array();
	$max_count = 0;
	$max_num = 0;
	for($i = $item_count; $i >= 0; $i--){
		//Data can be taken from DB
		/*$_req = "SELECT COUNT(id) AS id_count FROM 'table_name' WHERE CAST((UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(created))$seconds_divider AS SIGNED) = $i";
		$_res = mysqli_query($sql_conn, $_req);
		$_count = mysqli_fetch_array($_res, MYSQLI_ASSOC)["id_count"];*/

		//For testing use random data
		$_count = intval(rand(300, 1000) * (abs(sin($i / $item_count * M_PI * 2.0)) + 0.5));

		array_push($counts, $_count);
		if($_count > $max_count){
			$max_count = $_count;
			$max_num = $item_count - $i;
		}
	}
	if($max_count > 0){
		for($i = 0; $i < $item_count; $i++){
			$_x1 = $i / ($item_count - 1) * $width;
			$_y1 = $end_y - $counts[$i] / $max_count * $height;
			$_x2 = ($i + 1) / ($item_count - 1) * $width;
			$_y2 = $end_y - $counts[$i + 1] / $max_count * $height;
			if($i == $max_num){
				imageline($img, $_x1, $end_y, $_x1, $_y1, $divider_color);
			}
			else{
				//Draw dashed line
				$_pos = $end_y;
				while(true)
				{
					$_y = $_pos - 1;
					if($_y < $_y1){
						$_y = $_y1;
					}
					imageline($img, $_x1, $_pos, $_x1, $_y, $divider_color);
					$_pos -= 10;
					if($_pos <= $_y1){
						break;
					}
				}
			}
			imageline($img, $_x1, $_y1, $_x2, $_y2, $line_color);
		}
	}
	else{
		imageline($img, $start_x, $end_y, $end_x, $end_y, $line_color);
	}
	imagestring($img, 3, $start_x + $width * 0.5, $end_y - 9, "MAX: " . $max_count, $text_color);
}

?>
