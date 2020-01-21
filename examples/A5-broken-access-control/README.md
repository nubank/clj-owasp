# A5 - Broken access control
Restrictions on what authenticated users are allowed to do are often not properly enforced. Attackers can exploit these flaws to access unauthorized functionality and/or data, such as access other users' accounts, view sensitive files, modify other users’ data, change access rights, etc.

# Example
This dummy code basically return the lyrics from a music reading it from the disk.
```clojure
(ns broken-access-control)

(defn get-lyric [music-name]
  (->> music-name
       (str "/tmp/lyrics/")
       slurp))

(get-lyric "geni-e-o-zepelim")
```

The problem here is that we get the input from the user and tries to read it directly from the file system. So if the user provide a bunch of `../` is possible to read any file like the example bellow.

```clojure
(get-lyric "../../../../../../../../../../../../../../../etc/passwd")
```

# Fix
Never get concatenate user input without validation, in this case you could have a map of available lyrics and the respective file path like following the example.

```clojure
(ns not-broken-access-control)

(def lyrics {:geni-e-o-zepelim "/tmp/lyrics/geni-e-o-zepelim"})

(defn get-lyric [music-name]
  (->> lyrics
       ((keyword music-name))
       slurp))

(get-lyric "geni-e-o-zepelim")
```
