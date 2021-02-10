<template>
  <h1 class="mt-5">Vanilla RSC Overall XP Change HiScore</h1>
  <!--
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
  -->
  <p><syncro-result-component /></p>
  <days-before-today-navigation-component
    :isNavigationDisabled="isNavigationDisabled"
    class="mt-5"
  />
  <router-view v-on:onDataLoading="onDataLoading" v-on:onDataLoaded="onDataLoaded" />
</template>

<script>
import DaysBeforeTodayNavigationComponent from "./components/DaysBeforeTodayNavigationComponent";
import SyncroResultComponent from "./components/SyncroResultComponent";

export default {
  components: { DaysBeforeTodayNavigationComponent, SyncroResultComponent },
  name: "HomePage",
  data: function () {
    return {
      isNavigationDisabled: true,
    };
  },
  methods: {
    onDataLoading: function () {
      this.isNavigationDisabled = true;
    },
    onDataLoaded: function () {
      this.isNavigationDisabled = false;
    },
  },
};
</script>
