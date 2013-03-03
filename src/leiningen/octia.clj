(ns leiningen.octia
  (:use [leiningen.help :only (help-for subtask-help-for)])
  (:use [leiningen.octia.doc :only [doc]]))

(defn- nary? [v n]
  (some #{n} (map count (:arglists (meta v)))))

(defn octia
  "Octia Leiningen utilities."
  {:help-arglists '([doc])
   :subtasks [#'doc]}
  ([project]
     (println (if (nary? #'help-for 2)
                (help-for project "octia")
                (help-for "octia"))))
  ([project subtask & args]
     (case subtask
       "doc"  (apply doc project args)
              (println "Subtask" (str \" subtask \") "not found."
                       (subtask-help-for *ns* #'octia)))))
