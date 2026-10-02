// 88WELLNESS APP INTERACTIVE LOGIC (SENIOR-FRIENDLY & GROUP-ORIENTED)

let currentSelectedStage = 1; // 1: 기초, 2: 심화, 3: 기능/소도구
let currentSelectedPosition = 1; // 1..4
let currentSelectedResistance = 1; // 1..4
let currentSelectedMonth = 1; // 1..3

// Demographic survey data
let userDemographics = {
  ageGroup: "60대",
  gender: "여성",
  email: "",
  occupation: "주부",
  laborIntensity: "보통",
  discomfortAreas: []
};

// ----------------------------------------------------
// DEMOGRAPHICS & TEST NAVIGATION
// ----------------------------------------------------
function startDemographics() {
  const intro = document.getElementById('demographics-intro');
  const form = document.getElementById('demographics-form');
  if (intro) intro.classList.add('hidden');
  if (form) {
    form.classList.remove('hidden');
    form.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }
}

function selectAge(btn, val) {
  userDemographics.ageGroup = val;
  document.querySelectorAll('.age-btn').forEach(b => {
    b.className = "age-btn px-4 py-2.5 rounded-xl border border-slate-200 bg-white text-slate-700 text-xs font-bold transition";
  });
  btn.className = "age-btn px-4 py-2.5 rounded-xl border-2 border-blue-600 bg-blue-50 text-blue-700 text-xs font-black transition";
}

function selectGender(btn, val) {
  userDemographics.gender = val;
  document.querySelectorAll('.gender-btn').forEach(b => {
    b.className = "gender-btn px-6 py-2.5 rounded-xl border border-slate-200 bg-white text-slate-700 text-xs font-bold transition";
  });
  btn.className = "gender-btn px-6 py-2.5 rounded-xl border-2 border-blue-600 bg-blue-50 text-blue-700 text-xs font-black transition";
}

function toggleDiscomfort(btn, val) {
  const idx = userDemographics.discomfortAreas.indexOf(val);
  if (idx > -1) {
    userDemographics.discomfortAreas.splice(idx, 1);
    btn.className = "discomfort-btn px-3.5 py-2 rounded-xl border border-slate-200 bg-white text-slate-700 text-xs font-bold transition";
  } else {
    userDemographics.discomfortAreas.push(val);
    btn.className = "discomfort-btn px-3.5 py-2 rounded-xl border-2 border-blue-600 bg-blue-50 text-blue-700 text-xs font-black transition";
  }
}

function startQuestionsFromForm(e) {
  if (e) e.preventDefault();
  const emailInput = document.getElementById('demo-email');
  if (emailInput && emailInput.value) {
    userDemographics.email = emailInput.value.trim();
  }
  const occSelect = document.getElementById('demo-occupation');
  if (occSelect) userDemographics.occupation = occSelect.value;
  const laborSelect = document.getElementById('demo-labor');
  if (laborSelect) userDemographics.laborIntensity = laborSelect.value;

  const form = document.getElementById('demographics-form');
  const qContainer = document.getElementById('q-container');
  if (form) form.classList.add('hidden');
  if (qContainer) {
    qContainer.classList.remove('hidden');
    currentQ = 0;
    answers = [];
    showQuestion(0);
    qContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }
}

// ----------------------------------------------------
// 4x4 MATRIX LOGIC
// ----------------------------------------------------
function initMatrixGrid() {
  const tbody = document.getElementById('matrix-grid-body');
  if (!tbody) return;
  tbody.innerHTML = '';

  const posRows = [
    { id: 1, label: "P1: 누운/엎드린" },
    { id: 2, label: "P2: 엎드린/네발" },
    { id: 3, label: "P3: 네발/앉은" },
    { id: 4, label: "P4: 앉은/선 자세" }
  ];

  posRows.forEach(row => {
    const tr = document.createElement('tr');
    tr.className = "border-t border-slate-200/80";

    const th = document.createElement('td');
    th.className = "p-2.5 text-xs font-bold text-slate-800 bg-slate-100/40 whitespace-nowrap";
    th.innerText = row.label;
    tr.appendChild(th);

    for (let r = 1; r <= 4; r++) {
      const td = document.createElement('td');
      td.className = "p-1.5 text-center";

      const key = `${currentSelectedStage}_${row.id}_${r}`;
      const ex = matrixData[key];

      const isSelected = (row.id === currentSelectedPosition && r === currentSelectedResistance);

      const btn = document.createElement('button');
      btn.id = `mcell-${key}`;
      btn.onclick = () => selectMatrixCell(row.id, r);
      btn.className = isSelected
        ? "w-full p-2.5 rounded-xl bg-blue-600 text-white font-extrabold text-xs shadow-md border-2 border-blue-400 transition-all scale-105"
        : "w-full p-2.5 rounded-xl bg-white hover:bg-blue-50 text-slate-700 hover:text-blue-700 font-bold text-xs border border-slate-200 transition-all";

      const shortTitle = ex ? (ex.title.length > 14 ? ex.title.substring(0, 13) + '..' : ex.title) : '처방';
      btn.innerHTML = `<span class="block truncate max-w-[130px] mx-auto">${shortTitle}</span>`;
      td.appendChild(btn);
      tr.appendChild(td);
    }

    tbody.appendChild(tr);
  });

  renderMatrixDetail();
}

function selectMatrixCell(p, r) {
  currentSelectedPosition = p;
  currentSelectedResistance = r;
  initMatrixGrid();
}

function renderMatrixDetail() {
  const key = `${currentSelectedStage}_${currentSelectedPosition}_${currentSelectedResistance}`;
  const ex = matrixData[key] || matrixData["1_1_1"];

  const stageBadge = currentSelectedStage === 1 ? "1단계: 기초/자유도 적응" : (currentSelectedStage === 2 ? "2단계: 변형/심화" : "3단계: 소도구/기능");
  document.getElementById('md-stage-badge').innerText = stageBadge;
  document.getElementById('md-pos-badge').innerText = ex.posLabel;
  document.getElementById('md-res-badge').innerText = ex.resLabel;
  document.getElementById('md-time-badge').innerText = `⏱ ${ex.duration}초 (${ex.reps})`;
  document.getElementById('md-title').innerText = ex.title;
  document.getElementById('md-benefit').innerText = `✨ ${ex.benefit}`;

  const instrEl = document.getElementById('md-instructions');
  instrEl.innerHTML = '';
  ex.instructions.forEach((ins, idx) => {
    const li = document.createElement('li');
    li.className = "flex items-start gap-2";
    li.innerHTML = `<span class="font-bold text-blue-600">${idx+1}.</span> <span>${ins}</span>`;
    instrEl.appendChild(li);
  });

  document.getElementById('md-safety').innerText = ex.safety;

  const linkedWk = roadmapWeeks.find(w => w.week === ex.linkedWeek) || roadmapWeeks[0];
  document.getElementById('md-linked-week').innerText = linkedWk.title;
}

function setMatrixStage(stageNum) {
  currentSelectedStage = stageNum;
  for (let i = 1; i <= 3; i++) {
    const btn = document.getElementById(`matrix-m${i}`);
    if (btn) {
      if (i === stageNum) {
        btn.className = "px-4 py-2 rounded-full text-xs font-black bg-blue-600 text-white shadow-md transition";
      } else {
        btn.className = "px-4 py-2 rounded-full text-xs font-black bg-slate-100 text-slate-600 hover:bg-slate-200 transition";
      }
    }
  }
  initMatrixGrid();
}

function jumpToLinkedRoadmap() {
  const key = `${currentSelectedStage}_${currentSelectedPosition}_${currentSelectedResistance}`;
  const ex = matrixData[key] || matrixData["1_1_1"];
  const linkedMonth = Math.ceil(ex.linkedWeek / 4);
  selectMonth(linkedMonth);
  window.location.hash = "#roadmap";
  setTimeout(() => {
    const el = document.getElementById(`week-card-${ex.linkedWeek}`);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth', block: 'center' });
      el.classList.add('ring-4', 'ring-blue-500');
      setTimeout(() => el.classList.remove('ring-4', 'ring-blue-500'), 2000);
    }
  }, 300);
}

// ----------------------------------------------------
// 12-WEEK ROADMAP LOGIC (CLEAN SENIOR-FRIENDLY VIEW)
// ----------------------------------------------------
function selectMonth(m) {
  currentSelectedMonth = m;
  for (let i = 1; i <= 3; i++) {
    const tab = document.getElementById(`month-tab-${i}`);
    if (tab) {
      if (i === m) {
        tab.className = "month-tab px-5 py-3 rounded-2xl font-black text-sm transition-all bg-blue-600 text-white shadow-lg shadow-blue-500/20 whitespace-nowrap";
      } else {
        tab.className = "month-tab px-5 py-3 rounded-2xl font-black text-sm transition-all bg-white text-slate-600 hover:bg-slate-100 border border-slate-200 whitespace-nowrap";
      }
    }
  }

  const banners = {
    1: { title: "1달차 핵심 원칙: 자유도 순차 적응 (1~4주차)", desc: "누운 자세(P1)부터 서 있는 자세(P4)까지 주차별로 자유도를 한 단계씩 올리며 통증 없는 지지 코어를 형성합니다." },
    2: { title: "2달차 핵심 원칙: 운동 동작 다양화 & 심화 (5~8주차)", desc: "1달차와 동일한 자유도 흐름을 반복하되 편측 지지, 텐덤 보행 등 운동 동작을 다채롭게 확장합니다." },
    3: { title: "3달차 핵심 원칙: 소도구 & 일상생활(ADL) 기능 전이 (9~12주차)", desc: "세라밴드, 미니볼, 스텝박스를 활용하여 계단 오르기, 짐 들기 등 실생활 독립 활력을 완성합니다." }
  };
  document.getElementById('month-concept-title').innerText = banners[m].title;
  document.getElementById('month-concept-desc').innerText = banners[m].desc;

  renderRoadmapWeeks();
}

function renderRoadmapWeeks() {
  const container = document.getElementById('weeks-container');
  if (!container) return;
  container.innerHTML = '';

  const weeksInMonth = roadmapWeeks.filter(w => w.month === currentSelectedMonth);

  weeksInMonth.forEach(wk => {
    const card = document.createElement('div');
    card.id = `week-card-${wk.week}`;
    card.className = "bg-white border-2 border-slate-200/90 rounded-3xl p-6 sm:p-7 shadow-sm hover:shadow-md transition-all";

    let evalBadge = '';
    if (wk.isEval) {
      evalBadge = `<div class="p-3.5 rounded-2xl bg-amber-50 border border-amber-200 text-amber-900 font-extrabold text-sm mb-4 flex items-center justify-between">
        <span class="flex items-center gap-2">⭐ ${wk.evalTitle}</span>
        <button onclick="window.location.hash='#test'" class="px-3.5 py-1.5 rounded-xl bg-amber-600 text-white font-bold text-xs hover:bg-amber-700 shadow-sm">재평가 측정</button>
      </div>`;
    }

    // Senior-friendly clear exercise list (no small icons or complicated sub-menus)
    let exListHtml = '';
    wk.exercises.forEach((exKey, idx) => {
      const ex = matrixData[exKey];
      if (!ex) return;
      exListHtml += `
        <div class="p-4 rounded-2xl bg-slate-50 border border-slate-200/80 hover:bg-blue-50/40 transition">
          <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-3">
            <div class="flex items-start gap-3">
              <span class="w-8 h-8 rounded-full bg-blue-600 text-white font-black text-sm flex items-center justify-center shrink-0 mt-0.5">${idx+1}</span>
              <div>
                <div class="flex flex-wrap items-center gap-1.5 mb-1">
                  <span class="px-2.5 py-0.5 rounded-md bg-blue-100 text-blue-700 font-extrabold text-[11px]">${ex.posLabel.split('(')[0]}</span>
                  <span class="px-2.5 py-0.5 rounded-md bg-teal-100 text-teal-800 font-extrabold text-[11px]">${ex.resLabel.split(':')[0]}</span>
                  <span class="text-xs text-slate-500 font-semibold">⏱ ${ex.duration}초 (${ex.reps})</span>
                </div>
                <strong class="text-slate-900 text-base font-black block">${ex.title}</strong>
                <p class="text-xs text-blue-600 font-bold mt-0.5">✨ ${ex.benefit}</p>
                <p class="text-xs text-slate-600 mt-1 leading-relaxed">${ex.instructions[0]}</p>
              </div>
            </div>

            <div class="flex items-center gap-2 shrink-0 self-end sm:self-center">
              <button onclick="speakKorean('${ex.title}. ${ex.benefit}. ${ex.instructions.join('. ')}')" class="px-3 py-2 rounded-xl bg-white border border-slate-200 hover:bg-blue-50 text-blue-700 text-xs font-bold flex items-center gap-1 shadow-sm">
                <span>🔊</span> 음성안내
              </button>
              <button onclick="startSingleExercise('${exKey}')" class="px-3.5 py-2 rounded-xl bg-blue-600 hover:bg-blue-700 text-white text-xs font-black shadow-md flex items-center gap-1">
                <span>▶</span> 운동실행
              </button>
            </div>
          </div>
        </div>
      `;
    });

    card.innerHTML = `
      ${evalBadge}
      <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-5 pb-4 border-b border-slate-100">
        <div>
          <div class="flex items-center gap-2 mb-1.5">
            <span class="px-3.5 py-1 rounded-full bg-blue-600 text-white text-xs font-black">${wk.week}주차</span>
            <span class="text-xs text-slate-600 font-bold">${wk.focus}</span>
          </div>
          <h3 class="text-2xl font-black text-slate-900">${wk.title}</h3>
          <p class="text-xs text-slate-600 mt-1">${wk.desc}</p>
        </div>

        <button onclick="startModalWorkoutForWeek(${wk.week})" class="px-6 py-3.5 rounded-2xl bg-blue-600 hover:bg-blue-700 text-white font-black text-sm flex items-center justify-center gap-2 shadow-lg shadow-blue-500/20 whitespace-nowrap transition">
          <span>▶</span> 이번 주 가이드 운동 전체 시작
        </button>
      </div>

      <div class="space-y-3">
        ${exListHtml}
      </div>
    `;

    container.appendChild(card);
  });
}

function openMatrixFromWeek(key) {
  const parts = key.split('_');
  currentSelectedStage = parseInt(parts[0]);
  currentSelectedPosition = parseInt(parts[1]);
  currentSelectedResistance = parseInt(parts[2]);
  setMatrixStage(currentSelectedStage);
  initMatrixGrid();
  window.location.hash = "#matrix";
  setTimeout(() => {
    document.getElementById('matrix-detail-card').scrollIntoView({ behavior: 'smooth', block: 'center' });
  }, 300);
}

function startSingleExercise(key) {
  const ex = matrixData[key];
  if (!ex) return;
  activePlayerExercises = [ex];
  activeExIndex = 0;
  document.getElementById('wp-week-tag').innerText = "단일 운동 실행";
  loadPlayerExercise();
  document.getElementById('workout-player-modal').classList.remove('hidden');
}

// ----------------------------------------------------
// WORKOUT TIMER MODAL LOGIC
// ----------------------------------------------------
let playerTimer = null;
let playerSeconds = 40;
let isPlayerRunning = false;
let activePlayerExercises = [];
let activeExIndex = 0;

function startModalWorkoutForWeek(weekNum) {
  const wk = roadmapWeeks.find(w => w.week === weekNum) || roadmapWeeks[0];
  activePlayerExercises = wk.exercises.map(k => matrixData[k]);
  activeExIndex = 0;
  document.getElementById('wp-week-tag').innerText = wk.title;
  loadPlayerExercise();
  document.getElementById('workout-player-modal').classList.remove('hidden');
}

function startModalWorkoutFromMatrix() {
  const key = `${currentSelectedStage}_${currentSelectedPosition}_${currentSelectedResistance}`;
  const ex = matrixData[key] || matrixData["1_1_1"];
  activePlayerExercises = [ex];
  activeExIndex = 0;
  document.getElementById('wp-week-tag').innerText = "4×4 매트릭스 운동 실행";
  loadPlayerExercise();
  document.getElementById('workout-player-modal').classList.remove('hidden');
}

function loadPlayerExercise() {
  const ex = activePlayerExercises[activeExIndex];
  document.getElementById('wp-exercise-title').innerText = ex.title;
  document.getElementById('wp-reps').innerText = `${activeExIndex + 1} / ${activePlayerExercises.length} 운동 · ${ex.reps}`;
  document.getElementById('wp-instruction').innerText = ex.instructions[0];
  playerSeconds = ex.duration;
  document.getElementById('wp-timer').innerText = playerSeconds;

  clearInterval(playerTimer);
  isPlayerRunning = true;
  document.getElementById('wp-play-btn').innerText = "일시정지 ⏸";
  speakKorean(`${ex.title} 운동을 시작합니다.`);

  playerTimer = setInterval(() => {
    if (isPlayerRunning) {
      playerSeconds--;
      document.getElementById('wp-timer').innerText = playerSeconds;
      if (playerSeconds <= 0) {
        clearInterval(playerTimer);
        speakKorean("수고하셨습니다. 운동을 완료했습니다.");
        if (activeExIndex < activePlayerExercises.length - 1) {
          activeExIndex++;
          loadPlayerExercise();
        } else {
          alert("축하합니다! 오늘의 가이드 운동을 성공적으로 마쳤습니다.");
          closeWorkoutPlayer();
        }
      }
    }
  }, 1000);
}

function togglePlayerTimer() {
  isPlayerRunning = !isPlayerRunning;
  document.getElementById('wp-play-btn').innerText = isPlayerRunning ? "일시정지 ⏸" : "재개하기 ▶";
}

function prevExercise() {
  if (activeExIndex > 0) {
    activeExIndex--;
    loadPlayerExercise();
  }
}

function nextExercise() {
  if (activeExIndex < activePlayerExercises.length - 1) {
    activeExIndex++;
    loadPlayerExercise();
  } else {
    alert("마지막 운동입니다!");
  }
}

function closeWorkoutPlayer() {
  clearInterval(playerTimer);
  isPlayerRunning = false;
  document.getElementById('workout-player-modal').classList.add('hidden');
}

// ----------------------------------------------------
// TTS & AUDIO
// ----------------------------------------------------
function speakKorean(text) {
  if ('speechSynthesis' in window) {
    window.speechSynthesis.cancel();
    const utter = new SpeechSynthesisUtterance(text);
    utter.lang = 'ko-KR';
    utter.rate = 0.95;
    window.speechSynthesis.speak(utter);
  }
}

function speakMatrixExercise() {
  const key = `${currentSelectedStage}_${currentSelectedPosition}_${currentSelectedResistance}`;
  const ex = matrixData[key] || matrixData["1_1_1"];
  const textToSpeak = `${ex.title}. ${ex.benefit}. ${ex.instructions.join('. ')}. 주의 사항: ${ex.safety}`;
  speakKorean(textToSpeak);
}

// ----------------------------------------------------
// 5 SFMA QUESTIONS & MEMBERSHIP GATING
// ----------------------------------------------------
const questions = [
  {
    title: "1. FLEXION (척추 굴곡 & 상체 숙이기)",
    desc: "서서 상체를 앞으로 부드럽게 숙였을 때 손끝이 무릎 아래로 내려가며 허리가 시원한가요?",
    emoji: "🧘",
    check: "허리가 둥글게 자연스러운 곡선을 그리고 허벅지 뒤쪽 당김이 편안하게 견딜 만한지 확인하세요.",
    tag: "동작 1: 척추 굴곡 & 후면 체인"
  },
  {
    title: "2. EXTENSION (척추 신전 & 가슴 젖히기)",
    desc: "양손을 골반 뒤에 얹고 가슴을 천장 방향으로 부드럽게 젖혀 몸의 앞쪽 사슬을 열 수 있나요?",
    emoji: "🦒",
    check: "허리가 꺾이지 않고 등 윗부분(흉추)이 시원하게 펴지며 호흡이 편안한지 확인하세요.",
    tag: "동작 2: 척추 신전 & 전면 체인"
  },
  {
    title: "3. ROTATION (몸통 좌우 회전 가동성)",
    desc: "의자에 바르게 앉아 골반을 고정한 채 몸통을 좌우로 45도 이상 돌려 뒤를 바라볼 수 있나요?",
    emoji: "🔄",
    check: "좌우 회전 각도가 비슷하고 목이나 등에 찌르는 듯한 불편감이 없는지 확인하세요.",
    tag: "동작 3: 흉추 회전 & 나선 체인"
  },
  {
    title: "4. SINGLE LEG STANCE (한 발 서기 균형)",
    desc: "필요시 벽을 손끝으로 가볍게 스치듯 대고 한 발로 10초 이상 안정적으로 설 수 있나요?",
    emoji: "🦩",
    check: "골반이 옆으로 기울어지거나 발목이 심하게 흔들리지 않고 중심을 잡을 수 있는지 확인하세요.",
    tag: "동작 4: 편측 지지 & 외측 밸런스"
  },
  {
    title: "5. SQUAT (의자 스쿼트 & 하지 복합)",
    desc: "발을 어깨너비로 벌리고 의자에 앉았다가 손의 반동 없이 허벅지와 둔근 힘으로 일어설 수 있나요?",
    emoji: "🏋️",
    check: "무릎이 안으로 모이지 않고 고관절과 발목이 부드럽게 굽혀지는지 확인하세요.",
    tag: "동작 5: 하지 복합 & 일상 기립"
  }
];

let currentQ = 0;
let answers = [];
let currentRecommendation = { week: 1, stage: 1, pos: 1, res: 1 };

function answerQuestion(qIdx, type) {
  answers.push(type);
  currentQ++;
  if (currentQ < questions.length) {
    showQuestion(currentQ);
  } else {
    onTestFinished();
  }
}

function showQuestion(idx) {
  const q = questions[idx];
  document.getElementById('q-badge').innerText = (idx + 1) + " / 5 동작";
  document.getElementById('q-flow-tag').innerText = q.tag;
  document.getElementById('q-emoji').innerText = q.emoji;
  document.getElementById('q-title').innerText = q.title;
  document.getElementById('q-desc').innerText = q.desc;
  document.getElementById('q-check').innerText = q.check;
}

function onTestFinished() {
  document.getElementById('q-container').classList.add('hidden');
  const user = localStorage.getItem('88_user');
  if (user) {
    showResult();
  } else {
    // Show membership gating modal
    const gate = document.getElementById('membership-gate');
    if (gate) {
      gate.classList.remove('hidden');
      gate.scrollIntoView({ behavior: 'smooth', block: 'center' });
    } else {
      showResult();
    }
  }
}

function handleKakaoSignup() {
  const userData = {
    method: "kakao",
    name: "카카오회원",
    email: userDemographics.email || "kakao_user@88workout.com",
    signupDate: new Date().toISOString()
  };
  localStorage.setItem('88_user', JSON.stringify(userData));
  document.getElementById('membership-gate').classList.add('hidden');
  showResult();
}

function handleEmailSignup(e) {
  if (e) e.preventDefault();
  const userId = document.getElementById('reg-id').value;
  const userPw = document.getElementById('reg-pw').value;
  const userName = document.getElementById('reg-name').value;
  const userPhone = document.getElementById('reg-phone').value;
  const userGender = document.querySelector('input[name="reg-gender"]:checked')?.value || "여성";
  const userAddress = document.getElementById('reg-address').value;

  const userData = {
    method: "email",
    id: userId,
    name: userName,
    phone: userPhone,
    gender: userGender,
    address: userAddress,
    email: userDemographics.email,
    signupDate: new Date().toISOString()
  };
  localStorage.setItem('88_user', JSON.stringify(userData));
  document.getElementById('membership-gate').classList.add('hidden');
  showResult();
}

function skipOrLogin() {
  localStorage.setItem('88_user', JSON.stringify({ method: "guest", name: "88회원" }));
  document.getElementById('membership-gate').classList.add('hidden');
  showResult();
}

let latestReportText = "";

function sendReportEmail(characterName, mbti, summary, scores, recTitle, recDesc) {
  const targetEmail = userDemographics.email || "nexthealthcare8@gmail.com";
  const targetEl = document.getElementById('res-email-target-text');
  if (targetEl) {
    targetEl.innerText = `수신처: ${targetEmail} (스팸 메일함도 함께 확인해주세요)`;
  }

  latestReportText = `[88웰니스 신체검진 결과 분석 리포트]
--------------------------------------------------
■ 회원 인적 사항:
- 수신 이메일: ${targetEmail}
- 연령대: ${userDemographics.ageGroup}
- 성별: ${userDemographics.gender}
- 직업: ${userDemographics.occupation}
- 활동 강도: ${userDemographics.laborIntensity}
- 평소 불편한 곳: ${userDemographics.discomfortAreas.join(', ') || '특별히 없음'}

■ 신체 MBTI 동물 캐릭터 진단:
- 체형 유형: ${characterName} (${mbti})
- 핵심 진단: ${summary}

■ 4대 기능 영역 점수:
- 가동성 (Mobility): ${scores.mob}점
- 안정성 (Stability): ${scores.sta}점
- 균형감각 (Balance): ${scores.bal}점
- 근력파워 (Power): ${scores.pow}점

■ 맞춤 12주 처방 & 4×4 운동처방:
- 추천 시작점: ${recTitle}
- 처방 가이드: ${recDesc}
--------------------------------------------------
(주)넥스트헬스케어 · 88웰니스
대표이사 김재원 | 문의: nexthealthcare8@gmail.com`;

  // Real Email Dispatch via FormSubmit API
  try {
    fetch("https://formsubmit.co/ajax/nexthealthcare8@gmail.com", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({
        _subject: `[88웰니스] ${targetEmail}님의 신체검진 분석 리포트 (${characterName})`,
        _replyto: targetEmail,
        _cc: targetEmail,
        이메일: targetEmail,
        연령대: userDemographics.ageGroup,
        성별: userDemographics.gender,
        불편부위: userDemographics.discomfortAreas.join(', ') || '없음',
        진단유형: `${characterName} (${mbti})`,
        추천처방: recTitle,
        상세리포트: latestReportText
      })
    }).then(res => res.json()).then(data => {
      console.log("Email auto-sent successfully", data);
    }).catch(err => {
      console.log("Email dispatch status noted", err);
    });
  } catch (e) {
    console.warn("Mail dispatch error:", e);
  }
}

function copyReportText() {
  if (!latestReportText) return;
  if (navigator.clipboard) {
    navigator.clipboard.writeText(latestReportText).then(() => {
      alert("✅ 신체검진 분석 리포트 전문이 클립보드에 복사되었습니다!\n원하시는 곳(메모장, 카카오톡 등)에 붙여넣기 하실 수 있습니다.");
    }).catch(() => {
      prompt("아래 텍스트를 복사하세요:", latestReportText);
    });
  } else {
    prompt("아래 텍스트를 복사하세요:", latestReportText);
  }
}

function showResult() {
  const gate = document.getElementById('membership-gate');
  if (gate) gate.classList.add('hidden');
  document.getElementById('q-container').classList.add('hidden');
  document.getElementById('result-container').classList.remove('hidden');

  const hasPain = answers.includes('pain');
  const hasTight = answers.includes('tight');

  let charName = "";
  let mbti = "";
  let summary = "";
  let recTitle = "";
  let recDesc = "";
  let scores = {};

  if (hasPain) {
    charName = "당당한 호랑이";
    mbti = "P-CON (파워제어형)";
    summary = "“추진력과 근력은 강하지만 관절 충격 완화와 안전 감속 제어가 필수인 타입”";
    recTitle = "추천 시작점: 3주차 수직 중력 적응 & 흉추 회전 (P3 × R1)";
    recDesc = "무리한 기립 운동 대신 의자에 앉아 척추 압박을 줄이고 통증 없는 회전 가동성을 먼저 확보합니다.";
    scores = { mob: "60", sta: "70", bal: "65", pow: "88" };
    currentRecommendation = { week: 3, stage: 1, pos: 3, res: 1 };

    document.getElementById('res-emoji').innerText = "🐯";
    document.getElementById('res-desc').innerText = "근육 힘과 추진력은 훌륭하지만, 동작 시 관절 충격을 흡수하는 감속 제어력이 부족하여 통증이 생기기 쉽습니다. 3단계 좌식 감압 동작부터 통증 없는 안전 가동 범위를 유지하는 것이 핵심입니다.";
  } else if (hasTight) {
    charName = "듬직한 거북이";
    mbti = "S-MOB (안정성형)";
    summary = "“단단한 중심 안정성을 가졌으나 척추와 고관절 가동 범위 확장이 필요한 타입”";
    recTitle = "추천 시작점: 2주차 후면 사슬 & 4지 지지 (P2 × R1)";
    recDesc = "네발기기 자세에서 척추 마디마디를 부드럽게 풀고 굳은 어깨와 골반을 시원하게 스트레칭합니다.";
    scores = { mob: "45", sta: "85", bal: "68", pow: "60" };
    currentRecommendation = { week: 2, stage: 1, pos: 2, res: 1 };

    document.getElementById('res-emoji').innerText = "🐢";
    document.getElementById('res-desc').innerText = "신체 중심부는 단단하지만 척추와 고관절이 굳어 있어 보행 시 관절 피로가 쌓이기 쉽습니다. 네발기기 캣-카우 스트레칭 및 흉추 오픈 가동성으로 유연성을 확장하는 처방을 권장합니다.";
  } else {
    charName = "유연한 나무늘보";
    mbti = "M-STA (유연성형)";
    summary = "“부드러운 관절 가동성을 지녔으나 코어 중심 지지력이 필요한 힐링 체질”";
    recTitle = "추천 시작점: 1주차 척추 감압 & 호흡 코어 (P1 × R1)";
    recDesc = "지면과 밀착된 상태에서 척추 부담 없이 횡격막 호흡과 골반 틸팅으로 기초 코어를 깨웁니다.";
    scores = { mob: "88", sta: "48", bal: "55", pow: "50" };
    currentRecommendation = { week: 1, stage: 1, pos: 1, res: 1 };

    document.getElementById('res-emoji').innerText = "🦥";
    document.getElementById('res-desc').innerText = "몸이 부드러운 편이지만, 척추를 단단하게 잡아주는 코어 안정성과 둔근 지지력이 부족하여 오래 서 있거나 보행 시 피로를 쉽게 느낍니다. 바닥 자세부터 코어 지지력을 차근차근 다지는 것이 최고의 처방입니다.";
  }

  document.getElementById('res-mbti').innerText = mbti;
  document.getElementById('res-name').innerText = charName;
  document.getElementById('res-summary').innerText = summary;
  document.getElementById('res-rec-title').innerText = recTitle;
  document.getElementById('res-rec-desc').innerText = recDesc;
  document.getElementById('res-mob').innerText = scores.mob + "점";
  document.getElementById('res-sta').innerText = scores.sta + "점";
  document.getElementById('res-bal').innerText = scores.bal + "점";
  document.getElementById('res-pow').innerText = scores.pow + "점";

  // Trigger Automatic Email Sending!
  sendReportEmail(charName, mbti, summary, scores, recTitle, recDesc);

  document.getElementById('result-container').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

function resetTest() {
  currentQ = 0;
  answers = [];
  document.getElementById('result-container').classList.add('hidden');
  const intro = document.getElementById('demographics-intro');
  if (intro) intro.classList.remove('hidden');
  const form = document.getElementById('demographics-form');
  if (form) form.classList.add('hidden');
  const gate = document.getElementById('membership-gate');
  if (gate) gate.classList.add('hidden');
}

function goToRoadmapFromRecommendation() {
  const linkedMonth = Math.ceil(currentRecommendation.week / 4);
  selectMonth(linkedMonth);
  window.location.hash = "#roadmap";
  setTimeout(() => {
    const el = document.getElementById(`week-card-${currentRecommendation.week}`);
    if (el) el.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }, 300);
}

function goToMatrixFromRecommendation() {
  currentSelectedStage = currentRecommendation.stage;
  currentSelectedPosition = currentRecommendation.pos;
  currentSelectedResistance = currentRecommendation.res;
  setMatrixStage(currentSelectedStage);
  initMatrixGrid();
  window.location.hash = "#matrix";
  setTimeout(() => {
    document.getElementById('matrix-detail-card').scrollIntoView({ behavior: 'smooth', block: 'center' });
  }, 300);
}

// ----------------------------------------------------
// RESERVATION & SCROLL SPY
// ----------------------------------------------------
function handleReservation(e) {
  e.preventDefault();
  const name = document.getElementById('res-input-name').value;
  document.getElementById('res-msg').innerText = `✅ ${name}님, 무료 센터 방문 예약이 접수되었습니다! 담당 코치가 24시간 내 연락드립니다.`;
  document.getElementById('res-msg').classList.remove('hidden');
}

function switchBottomNav(target) {
  const tabs = ['test', 'roadmap', 'matrix', 'center'];
  tabs.forEach(t => {
    const el = document.getElementById('bnav-' + t);
    if (!el) return;
    const iconWrap = el.querySelector('div');
    const textSpan = el.querySelector('span');
    const svgIcon = el.querySelector('svg');
    if (t === target) {
      el.className = "bottom-nav-btn group flex flex-col items-center justify-center flex-1 py-1 text-blue-600 transition-all";
      iconWrap.className = "w-9 h-7 rounded-full flex items-center justify-center bg-blue-100/80 transition-all";
      if (svgIcon) svgIcon.classList.add('text-blue-600');
      if (textSpan) textSpan.className = "text-[11px] font-extrabold mt-0.5 tracking-tight text-blue-600";
    } else {
      el.className = "bottom-nav-btn group flex flex-col items-center justify-center flex-1 py-1 text-slate-500 hover:text-blue-600 transition-all";
      iconWrap.className = "w-9 h-7 rounded-full flex items-center justify-center group-hover:bg-slate-100 transition-all";
      if (svgIcon) svgIcon.classList.remove('text-blue-600');
      if (textSpan) textSpan.className = "text-[11px] font-bold mt-0.5 tracking-tight text-slate-500";
    }
  });
}

window.addEventListener('scroll', () => {
  const sections = [
    { id: 'center', el: document.getElementById('center') },
    { id: 'matrix', el: document.getElementById('matrix') },
    { id: 'roadmap', el: document.getElementById('roadmap') },
    { id: 'test', el: document.getElementById('test') }
  ];
  const scrollPos = window.scrollY + 250;
  for (const sec of sections) {
    if (sec.el && sec.el.offsetTop <= scrollPos) {
      switchBottomNav(sec.id);
      break;
    }
  }
});

// Boot up
window.addEventListener('DOMContentLoaded', () => {
  initMatrixGrid();
  selectMonth(1);
});
