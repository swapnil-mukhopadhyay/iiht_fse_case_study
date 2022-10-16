<h2>JAVA :</h2>


<h3>Book (8080) :</h3>
https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/java/executable_jars/book-0.0.1.jar

<h3>Author (8081) :</h3>
https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/java/executable_jars/author-0.0.1.jar

<h3>Reader (8082) :</h3>
https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/java/executable_jars/reader-0.0.1.jar

<h2>UI :</h2>

https://github.com/swapnil-mukhopadhyay/iiht_fse_case_study/raw/main/digital_books/aws/ui/digitalbooks-ui.zip

<h3>After downloading and unzipping : </h3>

sed -i 's/{BOOK_HOST}/<book ip>:8080/g' main.b1ac57201f097ba0.jar

sed -i 's/{AUTHOR_HOST}/<author ip>:8081/g' main.b1ac57201f097ba0.jar

sed -i 's/{READER_HOST}/<reader ip>:8082/g' main.b1ac57201f097ba0.jar

