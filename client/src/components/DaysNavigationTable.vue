<template>
  <navigation-bar />
  <router-view />
</template>

<script>
import NavigationBar from "./NavigationBar";
export default {
  name: "DaysNavigationTable",
  components: {
    NavigationBar,
  },
};
</script>

<style scoped></style>
