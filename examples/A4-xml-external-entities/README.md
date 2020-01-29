# A4 - XML External Entities
Many older or poorly configured XML processors evaluate external entity references within XML documents. External entities can be used to disclose internal files using the file URI handler, internal file shares, internal port scanning, remote code execution, and denial of service attacks.

# Example
In this example our application reaches a third-party API, receives a XML document and parses it.

```clojure
(ns sensitive-data-exposure 
  (:require [clojure.xml :as xml]))

(defn parse-document [xml-document]
  (xml/parse xml-document))

(defn get-document []
  (->
    (slurp "https://third-party.com/api/v1/clients/info")
    (.getBytes "UTF-8")
    java.io.ByteArrayInputStream.
    (parse-document)))
```

The main problem here is the `xml/parse` function which by default evaluate external entity references. So if our third-party gets compromised or have some internal attacker we could receive a malicious payload like the following.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE client [ <!ENTITY xxe SYSTEM "file:///etc/passwd"> ]>
<client>
    <username>matheus.bernardes</username> 
    <name>Matheus</name>
    <lastname>Bernardes</lastname>
    <score>100</score>
    <foo>&xxe;</foo>
</client>
```
That basically will make our parser evaluate the **ENTITY** reference and return the content of the file `/etc/passwd`.

# Fix
In order to fix this problem all you need to do is instance a sax parser with the `DOCTYPE` resolver disabled. This way the entity will never be evaluated. 

```clojure
(ns sensitive-data-exposure 
  (:require [clojure.xml :as xml]))

(defn startparse-sax-no-doctype [s ch]
  (..
    (doto (javax.xml.parsers.SAXParserFactory/newInstance)
      (.setFeature javax.xml.XMLConstants/FEATURE_SECURE_PROCESSING true)
      (.setFeature "http://apache.org/xml/features/disallow-doctype-decl" true))
    (newSAXParser)
    (parse s ch)))

(defn parse-document [xml-document]
  (xml/parse xml-document startparse-sax-no-doctype))

(defn get-document []
  (->
    (slurp "https://third-party.com/api/v1/clients/info")
    (.getBytes "UTF-8")
    java.io.ByteArrayInputStream.
    (parse-document)))
```
