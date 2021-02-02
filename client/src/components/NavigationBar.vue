<template>
    <ul class="nav nav-pills nav-fill">
        <li class="nav-item">
            <a class="nav-link" @click="setActive(1)" href="#" :class="{ active:isActive(1) }">1</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" @click="setActive(2)" href="#" :class="{ active:isActive(2) }">2</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" @click="setActive(3)" href="#" :class="{ active: isActive(3) }">3</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" @click="setActive(7)" href="#" :class="{ active: isActive(7) }">7</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" @click="setActive(14)" href="#" :class="{ active: isActive(14) }">14</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" @click="setActive(30)"  href="#" :class="{ active: isActive(30) }">30</a>
        </li>
    </ul>
</template>

<script>
export default {
  name: 'NavigationBar',
  data() {
      return {
          navItem: 1
      }
  },
  methods: {
        isActive: function (navItem) { return this.navItem === navItem },
        setActive: function (navItem) { 
            this.navItem = navItem
            this.$emit("selected", navItem)
         }
    }
}
</script>

<style scoped>
</style>