# Adding cognitive capability to play application

**Sample application for the blog post:**<BR />
https://pkhanal.github.io/post/add-cognitive-capability-to-play-application/

### Setup
- copy conf/sample.watson.conf to watson.conf
- copy credentials for Visual Recognition and Language translation service into conf/watson/conf

### Run
sbt run

### Test Image Classification endpoint

``
curl -G "http://localhost:9000/imageClassification” --data-urlencode “imageUrl=https://visual-recognition-demo.mybluemix.net/images/samples/5.jpg"
``

### Test Langauge Translation endpoint

``
curl -G "http://localhost:9000/translation" --data-urlencode "text=good bye" --data-urlencode "from=en" --data-urlencode "to=fr"
``


