# A8 - Insecure deserialization
Insecure deserialization often leads to remote code execution. Even if deserialization flaws do not result in remote code execution, they can be used to perform attacks, including replay attacks, injection attacks, and privilege escalation attacks.

# Example
In this example our application does the following:
- Remove dots from the strings
- Replace the comma with dot
- Transform the result into a double

```clojure
(ns playground
  (:require [clojure.string :as str]))

(defn- treat-dot-commas [word]
  (-> word
      (str/replace "." "")
      (str/replace "," ".")
      read-string))

(treat-dot-commas "1111.1111,30")
```

The main problem here is the `read-string` function which basically transform strings into Clojure objects. If you provide `"(+ 1 1)` to the `read-string` it will return a Clojure form that can be evaluated with `eval`.

```
(eval (read-string "(+ 1 1)"))
=> 2
```
Another approach is to use the [`reader eval #=`](https://clojure.org/guides/weird_characters#_reader_eval) which will evaluate the form in read time.

```clojure
(read-string "#=(+ 1 1)")
=> 2
```

# Fix
In order to fix this problem always use the `clojure.edn/read-string` because it does not evaluate the `reader eval`.

```clojure
(clojure.edn/read-string "#=(+ 1 1)")
Execution error at playground/eval12472 (playground.clj:13).
No dispatch macro for: =
```
