<template>
    <div id="month">
        <v-app id="inspire">
            <v-row class="fill-height">
                <v-col>
                    <v-sheet :style="{height : '64px', position : 'relative'}">
                    <div class="calendar-header">
                        <div id="month-prev" class="calendar-button" @click="$refs.calendar.prev()"><v-icon>fas fa-angle-left</v-icon></div>
                        <div class="calendar-title inline-p">
                            <v-toolbar-title v-if="$refs.calendar">{{ $refs.calendar.title }}</v-toolbar-title>
                        </div>
                        <div id="month-next" class="calendar-button" @click="$refs.calendar.next()"><v-icon>fas fa-angle-right</v-icon></div>
                    </div>
                    <div id="month-btn-add" class="calendar-button" @click="activeAdd = true"><v-icon>far fa-calendar-plus</v-icon></div>
                    </v-sheet>
                    <v-sheet height="800">
                    <v-calendar
                        ref="calendar"
                        v-model="focus"
                        color="var(--color-midBlue)"
                        :events="events"
                        :event-color="getEventColor"
                        :event-more="false"
                        type="month"
                        @click:event="openEvent"
                        @click:more="openEvents"
                        @change="getEventList"
                    >
                    <template v-slot:event="{event}">
                        <div :style="{'background-color' : event.color}" class="fill-height pl-2 event">{{event.name}}</div>
                    </template>
                    </v-calendar>
                    </v-sheet>
                </v-col>
            </v-row>
        </v-app>
        <v-dialog v-model="activeAdd" width="500">
            <v-card>
                <v-toolbar class="headline grey lighten-2 event-item-title" color="var(--color-grey-5)">
                    일정 등록
                </v-toolbar>
                <v-card-text>
                    <div class="event-item">
                        <table class="event-item-detail">
                            <tr>
                                <td>일정 내용 :</td>
                                <td><input id="month-content" type="text" v-model="add.name"/></td>
                            </tr>
                            <tr>
                                <td>일정 시작일 :</td>
                                <td><input type="date" v-model="add.startDay"/></td>
                            </tr>
                            <tr>
                                <td>일정 종료일 :</td>
                                <td><input type="date" v-model="add.endDay" :min="add.startDay"/></td>
                            </tr>
                            <tr>
                                <td>색상 :</td>
                                <td>
                                    <div @click="add.colorId=index;" :id="index" :class="{'event-color':true,'active-color' : add.colorId==index}" v-for="(color, index) in colors" :key="index" :style="{'background' : color}"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </v-card-text>
                <v-card-actions class="card-actions">
                    <button class="card-btn" @click="addEvent">확인</button>
                    <button class="card-btn" @click="closeAdd">취소</button>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-menu id="event-detail" v-model="showEvent" :close-on-content-click="false" max-width="350px" :activator="selectedElement" offset-y>
            <v-card color="grey lighten-4" min-width="350px" flat>
            <v-toolbar class="headline grey lighten-2 event-item-title" :color="colors[modify.colorId]">
                <input type="text" v-model="modify.name" :disabled="!activeModify"/>
                <v-spacer></v-spacer>
                <v-icon class="btn-event" @click="activeModify=true;" v-if="!activeModify">fas fa-pen</v-icon>
                <v-icon class="btn-event" @click="activeDelete=true;" v-if="!activeModify">fas fa-trash-alt</v-icon>
                <v-icon class="btn-event" @click="closeEvent">fas fa-times</v-icon>
            </v-toolbar>
            <v-card-text>
                <div class="event-item">
                    <table class="event-item-detail">
                        <tr>
                            <td>일정 시작일 :</td>
                            <td><input type="date" v-model="modify.startDay" :disabled="!activeModify"/></td>
                        </tr>
                        <tr>
                            <td>일정 종료일 :</td>
                            <td><input type="date" v-model="modify.endDay" :min="modify.startDay" :disabled="!activeModify"/></td>
                        </tr>
                        <tr v-if="activeModify">
                            <td>색상 :</td>
                            <td>
                                <div @click="modify.colorId=index;" :id="index" :class="{'event-color':true,'active-color' : modify.colorId==index}" v-for="(color, index) in colors" :key="index" :style="{'background' : color}"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </v-card-text>
            <v-card-actions class="card-actions" v-if="activeModify">
                <button class="card-btn" @click="modifyEvent">확인</button>
                <button class="card-btn" @click="cancleModify">취소</button>
            </v-card-actions>
            </v-card>
            <v-dialog v-model="activeDelete" width="300">
                <v-card>
                    <v-card-title id="delete-message">
                        일정을 삭제하시겠습니까?
                    </v-card-title>
                    <v-divider></v-divider>
                    <v-card-actions class="card-actions">
                        <button class="card-btn" @click="deleteEvent">예</button>
                        <button class="card-btn" @click="activeDelete=false;">아니오</button>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-menu>
    </div>
</template>

<script>
import http from '@/util/http-common.js';
export default {
    name : "Month",
    data() {
        return {
            focus : '',
            selectedEvent : {},
            selectedElement : null,
            selectedOpen : false,
            events : [],
            colors: ['var(--color-yellow2)', 'var(--color-green3)', 'var(--color-blue4)', 'var(--color-blue5)', 'var(--color-puple6)', 'var(--color-grey7)'],
            eventList : [],
            user : {},
            eventStyle : "",
            showEvent : false,
            showEvents : false,
            activeAdd : false,
            activeModify : false,
            activeDelete : false,
            modify : {
                id : 0,
                name : '',
                startDay : '',
                endDay : '',
                colorId : 0,
            },
            add :{
                name : '',
                startDay : '',
                endDay : '',
                colorId : 0,
            },
            selectDate : {
                date : undefined,
                year : 0,
                month : 0,
                day : 0,
            },
        }
    },
    created() {
        if(this.$store.state.user == null){
            this.$router.push("/");
        }
        this.user = this.$store.getters.getUser;
    },
    mounted() {
        this.$refs.calendar.checkChange()
    },
    updated() {
        document.getElementsByClassName('v-calendar-weekly__week').forEach((elem, index) => {
            elem.setAttribute("style","flex:1 auto !important");
        });
    },
    methods: {
        getEventColor (event) {
            return event.color
        },
        setToday () {
            this.focus = ''
        },
        openEvent ({ nativeEvent, event }) {
            const open = () => {
                this.selectedEvent = event;
                this.selectedElement = nativeEvent.target;
                this.modify.id = event.id;
                this.modify.name = event.name;
                this.modify.startDay = event.startDay;
                this.modify.endDay = event.endDay;
                this.modify.colorId = event.colorId;
                requestAnimationFrame(() => requestAnimationFrame(() => this.showEvent = true))
            }

            if (this.showEvent) {
                this.showEvent = false;
                requestAnimationFrame(() => requestAnimationFrame(() => open()))
            } else {
                open()
            }
            nativeEvent.stopPropagation()
        },
        closeEvent(){
            this.showEvent = false;
            this.activeModify = false;
            this.activeDelete = false;
            this.selectedElement = undefined;
            this.selectedEvent = undefined;
            this.modify = {
                id : 0,
                name : '',
                startDay : '',
                endDay : '',
                colorId : 0,
            };
        },
        addEvent(){
            const date = new Date(this.add.startDay);
            const event = {
                userId : this.user.userId,
                monYear : date.getFullYear(),
                monMonth : date.getMonth()+1,
                monStartDate : this.add.startDay,
                monEndDate : this.add.endDay,
                monContent : this.add.name,
                monColor : this.add.colorId,
            }
            if(event.monStartDate != '' && event.monEndDate != '' && event.monContent != ''){
                http.post('/v1/monthly', event)
                .then(()=>{
                    const start = { month : event.monMonth, year : event.monYear};
                    this.getEventList({ start });
                    this.closeAdd();
                });
            }
        },
        closeAdd(){
            this.activeAdd = false;
            this.add = {
                name : '',
                startDay : undefined,
                endDay : undefined,
                colorId : undefined,
            };
        },
        modifyEvent(){
            const date = new Date(this.modify.startDay);
            const event = {
                monId : this.modify.id,
                userId : this.user.userId,
                monYear : date.getFullYear(),
                monMonth : date.getMonth()+1,
                monStartDate : this.modify.startDay,
                monEndDate : this.modify.endDay,
                monContent : this.modify.name,
                monColor : this.modify.colorId,
            }
            http.put(`/v1/monthly`, event)
            .then(()=>{
                const start = { month : event.monMonth, year : event.monYear};
                this.getEventList({ start });
                this.closeEvent();
            })
        },
        cancleModify(){
            this.modify = {
                id : this.modify.id,
                name : this.selectedEvent.name,
                startDay : this.selectedEvent.startDay,
                endDay : this.selectedEvent.endDay,
                colorId : this.selectedEvent.colorId,
            };
            this.activeModify = false;
        },
        deleteEvent(){
            http.delete(`/v1/monthly/${this.selectedEvent.id}`)
            .then(()=>{
                const start = {month : this.selectedEvent.start.getMonth()+1, year : this.selectedEvent.start.getFullYear()};
                this.getEventList({start});
                this.closeEvent();
            })
        },
        openEvents({date}){
            const open = () => {
                const d = new Date(date);
                this.selectDate.date = d;
                this.selectDate.year = d.getFullYear();
                this.selectDate.month = d.getMonth()+1;
                this.selectDate.day = d.getDate();
                requestAnimationFrame(() => requestAnimationFrame(() => this.showEvents = true))
            }

            if (this.showEvent) {
                this.showEvents = false;
                requestAnimationFrame(() => requestAnimationFrame(() => open()))
            } else {
                open()
            }
        },
        closeEvents(){
            this.showEvents = false;
            this.activeAdd = false;
            this.selectDate = {
                date : undefined,
                year : 0,
                month : 0,
                day : 0,
            };
        },
        getEventList ({ start}) {
            http.get(`/v1/monthly/${this.user.userId}/${start.year}/${start.month}`).then(({data})=>{
                this.eventList = data.data;
                const events = [];
                const eventCount = this.eventList.length;
                for (let i = 0; i < eventCount; i++) {
                    const first = new Date(this.eventList[i].monStartDate);
                    const second = new Date(this.eventList[i].monEndDate);
                    events.push({
                        name: this.eventList[i].monContent,
                        start: first,
                        end: second,
                        color: this.colors[this.eventList[i].monColor],
                        colorId : this.eventList[i].monColor,
                        id : this.eventList[i].monId,
                        startDay : this.eventList[i].monStartDate,
                        endDay : this.eventList[i].monEndDate,
                    })
                }
                this.events = events
            });
        },
    },
}
</script>

<style scoped src="../../css/Calendar.css">
</style>