# ComposeFest2021
Using state in Jetpack Compose 본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

1. 영상 보러가기

    [![Video Label](https://img.youtube.com/vi/XXKmlKolcPk/0.jpg)](https://youtu.be/XXKmlKolcPk)

2. 슬라이드 자료 보러가기 👉 [링크](https://speakerdeck.com/veronikapj/2021-composefest2021-using-state-in-jetpack-compose)

## [Week 2-2] Using state in Jetpack Compose

### State

State는 앱 내부에서 **시간에 따라 변화하는 어떤 값**이다. 룸 db에 저장되는 값, 클래스의 필드, 가속도계로 부터 읽어 들이는 값 등이 해당된다.

안드로이드 앱은 이 `상태`를 보여주며 따라서 상태 관리가 매우 중요하다.

### Unidirectional data flow

- 데이터 전달은 일방 통행이자 싸이클을 이룬다.
- 안드로이드 앱은 이벤트를 기반으로 앱이 동작한다. 대부분의 클라이언트 앱에 해당된다 생각한다.
- Event -> Update state -> Display state -> Event...

> Event
>
> 앱 외부에서 들어오는 input. 사용자 입력, 센서로 부터 값 수신 이벤트 등

### 상태 관리

- 상태는 어떤 값이다. 또한 안드로이드 앱은 상태를 보여주어야 한다.

- 따라서 상태를 바꾸는 이벤트가 발생하면 상태를 추적하여 변경될 때마다 ui 업데이트를 해야한다.

- 그러나 이 모든 작업을 일일이 개발자가 연결시켜야 한다면 휴먼에러가 발생할 여지가 다분하다.

안드로이드에선 AAC(Android Architecture Component)의 `ViewModel`, `LiveData`를 제공하여 해당 문제를 쉽게 해결할 수 있도록 도와준다.

<img src="https://developer.android.com/codelabs/jetpack-compose-state/img/1bb3728573d00d8d.png" width=300/>



### ViewModel, Activity, LiveData

1. 액티비티로부터 이벤트가 발생하면 뷰모델에 이벤트로부터 발생한 어떤 행동(메서드)을 전달한다. 뷰모델의 메서드 호출
2. 이때 뷰모델은 상태(값)를 갖고있고 액티비티가 전달한 이벤트에 따라 상태(값)를 변경한다. 보통 상태는 LiveData를 이용하여 변경한다.
3. 액티비티의 ui 컴포넌트는 LiveData를 Observe하며 LiveData가 업데이트 될 때, ui 변경을 수행한다.

### Unidirectional data flow with AAC의 이점

- 테스트 용이
  - 상태와 ui를 분리하여 각각의 테스트가 용이하다.
- 상태 캡슐화
  - 뷰모델에서 상태를 모두 관리하므로, ui가 복잡해져도 부분적인 상태 ui 업데이트에 버그 발생 가능성을 줄일 수 있다.
- ui 일관성
  - LiveData로 상태 변경에 따른 ui 관리가 쉽다.

### Stateless Composable

Stateless Composable은 상태를 직접 변경하지 않는다. 단지 상태를 보여줄 뿐이다.

이점

- 테스트 용이
- 적은 버그
- 재사용 용이

그렇다면 상태변경은 어떻게 하나?

### State hoisting

컴포저블의 생성자에 인자 함수로 상태를 변경할 수 있는 함수를 받는다. 보통 뷰모델의 메서드가 될 것이다.

과정

- 외부의 이벤트로부터 상태 변경을 요청한다.
- 인자로 받은 상태 변경 함수를 호출한다.
- 상태가 변경되었고 컴포저블은 변경된 상태를 보여준다.

### Recomposition

Compose는 생성될 때, 트리 구조를 만든다. 이때, 트리는 android view 트리와는 다르다.

Recomposition은 Composable의 상태가 변할 때 마다 compose tree를 업데이트 하기위한 프로세스를 말한다.

Composable은 side-effect로부터 자유로워야 한다.

> side-effect
>
> Composable 함수 밖에서 일어나는 상태 변화.
>
> 상태가 변하면 recompostion이 일어나는데 side-effect로 인해 원치 않는 recompostion이 생길 수 있다.

### Stateful Composable

recompostion이 일어날 때, stateless composable은 매번 새로 그려진다.

따라서 recomposition이 일어나도 상태를 유지하려면 `remember`를 사용하여 상태를 저장한다.

**`remember` 사용시 주의사항**

- remember를 컨트롤할 필요가 있을 때, 매개변수로 설정한다.
- 그 외에는 로컬 변수로 설정한다.
- remember는 Composable이 지워지면 같이 지워진다. 따라서 중요한 정보는 remeber로 저장하지 말자.

### Built-in Composable

compose는 `undirectional data flow`를 위해 설계되었기 때문에 기존의 EditText처럼 상태 변화 리스너로 ui를 조작하는 건 맞지 않다. 컴포저블은 stateless인 버전을 최소 하나는 제공한다. 이는 stateful ui를 구성하기 위해 내부 상태가 없는 컴포저블이여야 하기 때문이다.(중복 상태 방지)

```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
