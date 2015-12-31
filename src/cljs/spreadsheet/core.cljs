(ns spreadsheet.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [spreadsheet.cell :refer [cell]]
              [re-frame.core :refer [dispatch-sync]]
              [spreadsheet.sheet]))

;; -------------------------
;; Views

(defn home-page []
  [:div 
   [:h1 "Spreadsheet"]
   [:h3 "A single page app test in Clojurescript"]
   [cell 0 0]
   [cell 0 1]
   [:div [:a {:href "/about"} "go to about page"]]])

(defn about-page []
  [:div [:h1 "About Spreadsheet"]
   [:div 
    [:p "This app intended to be a clone of Google Sheets
        to show how to implement a single page app in a 
        reactive functional manner"]]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (dispatch-sync [:initialize])
  (accountant/configure-navigation!)
  (accountant/dispatch-current!)
  (mount-root))
