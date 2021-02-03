import { createRouter, createWebHistory } from "vue-router";
// eslint-disable-next-line max-len
import PlayerOverallStateChangeTableComponent from "../components/main/PlayerOverallStateChangeTableComponent";
import NotFoundComponent from "../components/error/NotFoundComponent";
import App from "../App";

const routerHistory = createWebHistory();

const createDaysBeforeTodayRoute = function (daysBeforeToday) {
  return {
    name: `days-${daysBeforeToday}`,
    path: `${daysBeforeToday}`,
    component: PlayerOverallStateChangeTableComponent,
    props: { daysBeforeToday: daysBeforeToday },
  };
};

const routes = [
  {
    path: "/days",
    component: App,
    children: [
      { path: "/", redirect: { name: "days-1" } },
      { path: "/days", redirect: { name: "days-1" } },
      createDaysBeforeTodayRoute(1),
      createDaysBeforeTodayRoute(2),
      createDaysBeforeTodayRoute(3),
      createDaysBeforeTodayRoute(7),
      createDaysBeforeTodayRoute(14),
      createDaysBeforeTodayRoute(30),
    ],
  },
  { path: "/:pathMatch(.*)*", component: NotFoundComponent },
];

const router = createRouter({
  history: routerHistory,
  routes,
  linkExactActiveClass: "active",
});

export default router;
