(ns {{ns-name}}.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [{{ns-name}}.cli :as cli])
  (:gen-class))

(defn -main [& args]
  (let [{:keys [action options exit-message ok?]} (cli/validate-args args)]
    (if exit-message
      (cli/exit (if ok? 0 1) exit-message)
      (case action
        "start"  (println "starting server with: " options)
        "stop"   (println "stopping server with: " options)
        "status" (println "status   server with: " options)))))
