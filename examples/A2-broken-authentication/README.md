# A2 - Broken Authentication

Application functions related to authentication and session management are often implemented incorrectly, allowing attackers to compromise passwords, keys, or session tokens, or to exploit other implementation flaws to assume other users’ identities temporarily or permanently

# Example

This code basically simulates an application which register a new user on the database.

```clojure
(ns playground)

(def my-db (atom {}))

(defn add [table doc] (swap! my-db update-in [table] conj doc))

(defn register-new-user! [username password]
  (add :users {:username username :password password}))

(register-new-user! "matheus.bernardes" "banana")
```

The problem here is that this code saves the user password as plain text what totally breaks the authentication process.

# Fix

**ALWAYS** encrypt the users password.
