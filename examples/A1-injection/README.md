# A1-Injection
Injection flaws, such as SQL, NoSQL, OS, and LDAP injection, occur when untrusted data is sent to an interpreter as part of a command or query. The attacker’s hostile data can trick the interpreter into executing unintended commands or accessing data without proper authorization.

# Example
This code basically receives a certificate name and generates a RSA key in the `/tmp/a1/` path. 

```clojure
(ns injection)

(use '[clojure.java.shell :only [sh]])

(def key-path "/tmp/a1/")

(defn generate-rsa-key [{:keys [key-name]}]
  (let [key (str key-path key-name)
        command (str "openssl genrsa -out " key " 2048")]
    (sh "bash" "-c" command)))

(generate-rsa-key {:key-name "certificate"})
```

In order to exploit this code all you have to do is append bash commands on the filename as the following example.

```clojure
(generate-rsa-key {:key-name"; touch /tmp/a1/hacked #"})
```

# Fix
If you use the user input on your command never use the `bash -c` it allows the usage of bash functionalities like the semicolon which invoke another command after the previous one.

```clojure
(defn generate-rsa-key [{:keys [key-name]}]
  (let [key (str key-path key-name) ]
    (sh "openssl" "genrsa" "-out"  key " 2048")))
```
