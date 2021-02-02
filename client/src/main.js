import { createApp } from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import App from "./App.vue";
import router from "./router";

var app = createApp(App);
app.use(router);
axios.defaults.baseURL = process.env.VUE_APP_API_URL;
app.use(VueAxios, axios);
app.mount("#app");
