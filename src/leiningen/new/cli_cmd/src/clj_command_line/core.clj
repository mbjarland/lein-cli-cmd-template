(ns {{ns-name}}.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [{{ns-name}}.cli :as cli])
  (:gen-class))

(defn -main [& args]
  (let [{:keys [action options exit-message ok?]} (cli/validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      ;; the options at this point will be a map on the format
      ;; {:port 80, :hostname "localhost", :verbosity 0}
      ;; where the values are parsed rich objects if the command line specification
      ;; chooses to use :parse-fn 
      (case action
        "start" (println "starting server with: " options)
        "stop" (println "stopping server with: " options)
        "status" (println "status   server with: " options)))))
