<template>
  <div id="calendar">
    <div id="calendar-header">
      <img src="@/../public/Image/calendar_icon.png" id="calendar-icon">
      <p id="calendar-title">플래너</p>
    </div>
    <div id="calendar-container">
      <Monthly/>
    </div>
  </div>
</template>

<script>
import Monthly from '@/components/Library/Monthly';
export default {
  name : "Calendar",
  components : {
    Monthly,
  }
}
</script>

<style scoped src="../../css/Calendar.css">
</style>