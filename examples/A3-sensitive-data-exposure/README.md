# A3-Sensitive data exposure
Many web applications and APIs do not properly protect sensitive data, such as financial, healthcare, and PII. Attackers may steal or modify such weakly protected data to conduct credit card fraud, identity theft, or other crimes. Sensitive data may be compromised without extra protection, such as encryption at rest or in transit, and requires special precautions when exchanged with the browser.

# Example
This is a dummy example of a lambda which receives two value, input and components and only print it.

```clojure
(ns sensitive-data-exposure)

(def components
  "Statically defined, potentially expensive config / objects go here"
  (let [jetty-client (aws/default-http-client)
        {:nu.aws.lambda/keys [region]} mw.context/static]
    {:http-client jetty-client
     :okta-token  (okta/get-okta-token)
     :xray        (xray/client region jetty-client)}))

(defn consume!
  "Executed per invocation"
  [input components]
  (vis/info (pr-str input))
  (vis/info components))

(def -handler (mw/lambda-handler consume! components))
```

The main problem here is the `vis/info` function that is logging the components. This is a map which in this case contains a sensitive token from Okta.
This problem is most seeing during the development phase.

# Fix
Never log secrets but if it was necessary always rotate it.
