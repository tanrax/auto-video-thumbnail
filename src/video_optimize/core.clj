(ns video-optimize.core
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clj-yaml.core :as yaml]
   [hawk.core :as hawk]
   [clojure.java.shell :as shell]))

;; VARIABLES
(def config (yaml/parse-string (slurp "config.yaml")))
(def extension_thumbnail (:extension_thumbnail config))
(def width_thumbnail (:width_thumbnail config))
(def audio_quality_thumbnail (:audio_quality_thumbnail config))
(def path_videos (:path_videos config))

(defn convertVideo
  ;; Optimize video
  [e]
  (let [path_raw            (.getAbsolutePath (:file e))
        is_thumbnail        (doall (re-find (re-pattern extension_thumbnail) path_raw))
        path_thumbnail      (str  (str/join "." ( drop-last (str/split path_raw #"\."))) extension_thumbnail)
        path_thumbnail_temp (str "/tmp/" (last (str/split path_thumbnail #"\/")))]
    (prn path_thumbnail)
    (if (and (.exists (io/file path_raw)) (not is_thumbnail) (not (.exists (io/file path_thumbnail))))
      (do
        (prn (str "Optimizing: " path_raw))
        ;; Optimizing with ffmpeg
        (shell/sh "ffmpeg" "-y" "-i" path_raw "-vf" (str "scale=" width_thumbnail ":-2") "-c:v" "libx264" "-crf" "23" "-profile:v" "high" "-pix_fmt" "yuv420p" "-color_primaries" "1" "-color_trc" "1" "-colorspace" "1" "-movflags" "+faststart" "-an" "-acodec" "aac" "-ab" (str width_thumbnail "kb") path_thumbnail_temp)
        (shell/sh "mv" path_thumbnail_temp path_thumbnail)
        (prn (str "Finish: " path_thumbnail))))))

(defn -main [& args]
  ;; Watch
  (hawk/watch! [{:paths   [path_videos]
                 :handler (fn [ctx e]
                            (convertVideo e))}])
  (println "Running: Feed me!"))
