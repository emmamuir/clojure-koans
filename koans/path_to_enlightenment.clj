(use 'clojure.contrib.find-namespaces
     'clojure.test)

(def exit-code (atom 0))

(defn find-tests []
  (filter
   #(re-find #"-test" (str %)) (find-namespaces-in-dir (java.io.File. "."))))

(defn require-tests []
  
  (doseq [test (find-tests)]
    (require test)))

(require-tests)
(let [results (apply merge-with + (map test-ns (find-tests)))]
  (if (or (> (results :fail) 0)
          (> (results :error) 0))
    (System/exit -1)
    (System/exit 0)))