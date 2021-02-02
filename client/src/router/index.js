import { createRouter, createWebHistory } from "vue-router";
import DataTable from "../components/DataTable";

const routerHistory = createWebHistory();

const routes = [
  { path: "/", redirect: "/1" },
  {
    path: "/1",
    component: DataTable,
    name: "1",
  },
  {
    path: "/2",
    name: "2",
    component: DataTable,
  },
  {
    path: "/3",
    component: DataTable,
  },
  {
    path: "/7",
    component: DataTable,
  },
  {
    path: "/14",
    component: DataTable,
  },
  {
    path: "/30",
    component: DataTable,
  },
];

const router = createRouter({
  history: routerHistory,
  routes,
  linkExactActiveClass: "active",
});

export default router;
