<template>
  <ul class="nav nav-pills nav-fill">
    <li class="nav-item">
      <span v-if="isNavigationDisabled" class="nav-link disabled" :class="{ active: isActive(1) }">
        1 day
      </span>
      <router-link v-else :to="{ name: 'days-1' }" class="nav-link">1 day</router-link>
    </li>

    <li class="nav-item">
      <span v-if="isNavigationDisabled" class="nav-link disabled" :class="{ active: isActive(2) }">
        2 days
      </span>
      <router-link v-else :to="{ name: 'days-2' }" class="nav-link">2 days</router-link>
    </li>

    <li class="nav-item">
      <span v-if="isNavigationDisabled" class="nav-link disabled" :class="{ active: isActive(3) }">
        3 days
      </span>
      <router-link v-else :to="{ name: 'days-3' }" class="nav-link">3 days</router-link>
    </li>

    <li class="nav-item">
      <span v-if="isNavigationDisabled" class="nav-link disabled" :class="{ active: isActive(7) }">
        7 days
      </span>
      <router-link v-else :to="{ name: 'days-7' }" class="nav-link">7 days</router-link>
    </li>

    <li class="nav-item">
      <span v-if="isNavigationDisabled" class="nav-link disabled" :class="{ active: isActive(14) }">
        14 days
      </span>
      <router-link v-else :to="{ name: 'days-14' }" class="nav-link">14 days</router-link>
    </li>

    <li class="nav-item">
      <span v-if="isNavigationDisabled" class="nav-link disabled" :class="{ active: isActive(30) }">
        30 days
      </span>
      <router-link v-else :to="{ name: 'days-30' }" class="nav-link">30 days</router-link>
    </li>
  </ul>
</template>

<script>
export default {
  name: "DaysBeforeTodayNavigationComponent",
  props: ["isNavigationDisabled"],
  computed: {
    isDefault: function () {
      return this.$route.path === "/";
    },
  },
  methods: {
    clickLink: function (e) {
      if (this.isNavigationDisabled) {
        e.stopPropagation();
        return false;
      }
    },
    isActive: function (nr) {
      return this.$route.path === `/days/${nr}`;
    },
  },
};
</script>

<style scoped>
.active.disabled {
  background-color: grey !important;
}
.disabled {
  cursor: default;
}
</style>
