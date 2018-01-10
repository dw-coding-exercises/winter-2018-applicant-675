(ns my-exercise.search
  (:require [hiccup.page :refer [html5]]
            [clj-http.client :as client]))

(defn header [_]
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
     [:title "Find my next election"]
     [:link {:rel "stylesheet" :href "default.css"}]])

(defn election [response]
    (let [street (get (get response :params) :street)
          street-2 (get (get response :params) :street-2)
          city (get (get response :params) :city)
          state (get (get response :params) :state)
          zip (get (get response :params) :zip)
          curlresp (client/get (str "https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:"
                        (clojure.string/lower-case state) ",ocd-division/country:us/state:" (clojure.string/lower-case state)
                        "/place:" (clojure.string/lower-case city)))
          data (read-string (get curlresp :body))]
          [:div {:class "elections"}
            [:h1 (str "Here are your elections for " city ", " state)]
            [:h4 (str (get (peek data) :description))]
            [:p (str "on " (get (peek data) :date))]
        ]
    ))

(defn page [response]
    (html5
        (header response)
        (election response)))
