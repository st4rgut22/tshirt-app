Created an application selling t-shirts using Activities, Intents, and HTTP requests. 
For the server requests, I created a database on my local server. 
You can do the same to test this app by installing XAMPP on your computer, identifying your IP address, and replacing the string PRODUCT_URL in ShopActivity.java. 
Add the php file (below) in XAMPP/htdocs/api: 


    <?php
	define('DB_HOST','localhost');
	define('DB_USER','root');
	define('DB_PASS','');
	define('DB_NAME','tshirts');

	$conn = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
	if (mysqli_connect_errno()){
		die('Unable to connect to database'.mysqli_connect_error());
	}
	$stmt = $conn->prepare("SELECT id,price,title,img_url,bucket FROM tshirts;");

	$stmt->execute();

	$stmt->bind_result($id,$price,$title,$img_url,$bucket);

	$product = array();

	while ($stmt->fetch()){

		$temp = array();
		$temp['id']=$id;
		$temp['price']=$price;
		$temp['title']=$title;
		$temp['img_url']=$img_url;
		$temp['bucket']=$bucket;

		array_push($product,$temp);
	}

	echo json_encode($product);

Make sure you have also added the Glide and Volley libraries as dependencies to your project. 
I used Android Studio IDE to run the app. 

Here is a demo of my app: 

https://www.youtube.com/watch?v=EoPQnslVl9E
