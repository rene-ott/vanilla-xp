<template>
  <h1 class="mt-5">Vanilla RSC Overall XP Change HiScore</h1>
  <p class="w-50 p-2 border">
    The web application displays
    <a href="https://www.runescapeclassic.org/">RSC Vanilla</a>
    players
    <a href="https://www.runescapeclassic.org/hiscore/ranking/overall/">overall</a>
    XP change data. Also, it includes data data about level and hiscore rank.
    <br />
    The change is calculated in the days before today and is visible by selecting navigation pill.
    It is currently possible to display the change from today to 1, 2, 3, 7, 14, 30 days ago.
    <br />
    The data is updated once a day and the last update was:
    <em>2222.02.02</em>
  </p>
  <days-before-today-navigation-component class="mt-5" />

  <router-view>
    <router-view class="mb-5" />
  </router-view>
</template>

<script>
// eslint-disable-next-line max-len
import DaysBeforeTodayNavigationComponent from "./components/main/DaysBeforeTodayNavigationComponent";
export default {
  name: "App",
  components: {
    DaysBeforeTodayNavigationComponent,
  },
};
</script>

<style scoped></style>
