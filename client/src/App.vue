<template>
  <navigation-bar v-on:nav-link-clicked="onNavItemSelected" />
  <router-view />
</template>

<script>
import NavigationBar from "./components/NavigationBar.vue";

export default {
  name: "App",
  components: {
    NavigationBar,
  },
  beforeMount() {
    console.log(process.env);
  },
  methods: {
    onNavItemSelected: function (navItem) {
      console.log("TE" + navItem);
    },
  },
};
</script>
