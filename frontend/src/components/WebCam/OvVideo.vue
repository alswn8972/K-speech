<template>
	<video class="active-user" autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',
	props: {
		streamManager: Object,
	},
	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>
<style scoped src="../../css/Camera.css">
</style>