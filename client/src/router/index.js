import { createRouter, createWebHistory } from "vue-router";
import DataTable from "../components/DataTable";
import PathNotFound from "../components/PathNotFound";
import DaysNavigationTable from "../components/DaysNavigationTable";

const routerHistory = createWebHistory();

const routes = [
  {
    path: "/days",
    component: DaysNavigationTable,
    children: [
      { path: "/", redirect: { name: "days-1" } },
      { path: "/days", redirect: { name: "days-1" } },
      { name: "days-1", path: "1", component: DataTable, props: { daysBeforeToday: 1 } },
      { name: "days-2", path: "2", component: DataTable, props: { daysBeforeToday: 2 } },
      { name: "days-3", path: "3", component: DataTable, props: { daysBeforeToday: 3 } },
      { name: "days-7", path: "7", component: DataTable, props: { daysBeforeToday: 7 } },
      { name: "days-14", path: "14", component: DataTable, props: { daysBeforeToday: 14 } },
      { name: "days-30", path: "30", component: DataTable, props: { daysBeforeToday: 30 } },
    ],
  },
  { path: "/:pathMatch(.*)*", component: PathNotFound },
];

const router = createRouter({
  history: routerHistory,
  routes,
  linkExactActiveClass: "active",
});

export default router;
