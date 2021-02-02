<template>
  <ul class="nav nav-pills nav-fill">
    <li class="nav-item">
      <router-link to="/1" class="nav-link" @click="setActive(1)">1</router-link>
    </li>
    <li class="nav-item">
      <router-link to="/2" class="nav-link" @click="setActive(2)">2</router-link>
    </li>
    <li class="nav-item">
      <router-link to="/3" class="nav-link" @click="setActive(3)">3</router-link>
    </li>
    <li class="nav-item">
      <router-link to="/7" class="nav-link" @click="setActive(7)">7</router-link>
    </li>

    <li class="nav-item">
      <router-link to="/14" class="nav-link" @click="setActive(14)">14</router-link>
    </li>
    <li class="nav-item">
      <router-link to="/30" class="nav-link" @click="setActive(30)">30</router-link>
    </li>
  </ul>
</template>

<script>
export default {
  name: "NavigationBar",
  data() {
    return {
      navItem: 1,
    };
  },
  methods: {
    setActive: function (navItem) {
      this.navItem = navItem;
      //this.$emit("nav-link-clicked", navItem)
    },
  },
};
</script>

<style scoped></style>
