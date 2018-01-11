(ns leiningen.new.cli-cmd
    (:use [leiningen.new.templates :only [renderer name-to-path sanitize-ns ->files]]))

(def render (renderer "cli-cmd"))

(defn cli-cmd
      [name]
      (let [data {:name      name
                  :ns-name   (sanitize-ns name)
                  :sanitized (name-to-path name)}]
           (->files data
                    ["project.clj" (render "project.clj" data)]
                    ["boot/jar-preamble.sh" (render "boot/jar-preamble.sh" data)]
                    ["src/{{sanitized}}/core.clj" (render "src/clj_command_line/core.clj" data)]
                    ["src/{{sanitized}}/cli.clj" (render "src/clj_command_line/cli.clj" data)]
                    ["test/{{sanitized}}/core_test.clj" (render "test/clj_command_line/core_test.clj" data)])))