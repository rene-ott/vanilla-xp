<template>
  <ul class="nav nav-pills nav-fill">
    <li class="nav-item">
      <router-link :class="{ active: isDefault }" :to="{ name: 'days-1' }" class="nav-link">
        1 day
      </router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{ name: 'days-2' }" class="nav-link">2 days</router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{ name: 'days-3' }" class="nav-link">3 days</router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{ name: 'days-7' }" class="nav-link">7 days</router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{ name: 'days-14' }" class="nav-link">14 days</router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{ name: 'days-30' }" class="nav-link">30 days</router-link>
    </li>
  </ul>
</template>

<script>
export default {
  name: "NavigationBar",
  computed: {
    isDefault: function () {
      return this.$route.path === "/";
    },
  },
};
</script>

<style scoped></style>
