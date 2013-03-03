(ns leiningen.octia.doc
  (:require [leinjacker.deps :as deps]
            [octia.doc       :as doc])
  (:use [leinjacker.eval :only (eval-in-project)]))

(defn load-namespaces
  "Create require forms for each of the supplied symbols. This exists because
  Clojure cannot load and use a new namespace in the same eval form."
  [& syms]
  `(require
    ~@(for [s syms :when s]
        `'~(if-let [ns (namespace s)]
             (symbol ns)
             s))))

(defn doc-task
  "Generate iodocs documentation from project routes"
  [project {:keys [filename] :or {filename "iodocs.json"} :as options}]
  (eval-in-project
    project
    `(octia.doc/spit-doc ~(-> project :octia :routes)
                         '~filename)
    (load-namespaces
      'octia.doc
      (-> project :octia :routes))))

(defn doc
  "Generate iodocs from project endpoints definition
   Endpoints can be defined using [:octia :routes] entry in project)"
  ([project]
     (doc-task project {}))
  ([project filename]
     (doc-task project {:filename filename})))