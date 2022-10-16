<h2>KAFKA :</h2>
wget https://downloads.apache.org/kafka/3.3.1/kafka_2.13-3.3.1.tgz

<h2>JAVA :</h2>

<h3>Install JDK</h3>
sudo yum install java-1.8.0-openjdk

<h3>Book (8080) :</h3>
wget https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/java/executable_jars/book-0.0.1.jar

<h3>Author (8081) :</h3>
wget https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/java/executable_jars/author-0.0.1.jar

<h3>Reader (8082) :</h3>
wget https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/java/executable_jars/reader-0.0.1.jar

<h2>UI :</h2>

<h3>DigitalBooks-Ui</h3>

wget https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/ui/digitalbooks-ui.zip

unzip digitalbooks-ui.zip

sed -i 's/{BOOK_HOST}/<book_ip>/g' main.b1ac57201f097ba0.js

sed -i 's/{AUTHOR_HOST}/<author_ip>/g' main.b1ac57201f097ba0.js

sed -i 's/{READER_HOST}/<reader_ip>/g' main.b1ac57201f097ba0.js

pwd

<h3>Install Nginx</h3>

sudo amazon-linux-extras install nginx1

cd /usr/share/nginx/html

rm -rf *.*

rm -rf icons/

cp <pwd of digitalbooks-ui>/*.* .
  
sudo service nginx start
  
sudo service nginx stop
