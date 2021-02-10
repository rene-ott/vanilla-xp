<template>
  <svg
    v-if="rankIcon === 'UP'"
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    fill="currentColor"
    class="bi bi-arrow-up"
    viewBox="0 0 24 24"
  >
    <path
      fill-rule="evenodd"
      d="M8 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L7.5 2.707V14.5a.5.5 0 0 0 .5.5z"
    />
  </svg>

  <svg
    v-else-if="rankIcon === 'DOWN'"
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    fill="currentColor"
    class="bi bi-arrow-down"
    viewBox="0 0 24 24"
  >
    <path
      fill-rule="evenodd"
      d="M8 1a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L7.5 13.293V1.5A.5.5 0 0 1 8 1z"
    />
  </svg>
</template>

<script>
export default {
  props: ["rankChange"],
  name: "RankArrowComponent",
  computed: {
    rankIcon: function () {
      if (this.rankChange === null || this.rankChange === 0) {
        return "NONE";
      } else if (this.rankChange > 0) {
        return "UP";
      } else {
        return "DOWN";
      }
    },
  },
};
</script>

<style scoped>
.bi-arrow-up {
  color: green;
}

.bi-arrow-down {
  color: red;
}
</style>
