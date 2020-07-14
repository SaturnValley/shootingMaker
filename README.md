# shootingMaker
マ.リ.オ.メ.ー.カ.ーに触発されたシューティングゲームです．
# shootingMaker
マ.リ.オ.メ.ー.カ.ーに触発されたシューティングゲームです．  

## ゲームをプレイする場合
  shootingMakerディレクトリ内のshootingMaker.jarをダウンロードし，以下のディレクトリ構造に配置してください．
  Javaがインストールされており，jarファイルを実行できる環境であれば動作するはずです．

  親フォルダ
  |__ exstages    
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ (このパスに作成したステージが保存される)  
  |  
  |__ shootingMaker.jar  

## 制作意図
  プログラミング面での特徴は以下の２点
  * キャラクターの追加を簡単に行える
  * 異なる機能間の関係を疎にし，部分的な更新ができる
  詳細は後述

## 操作方法
* ゲームプレイ/テストプレイモード
キーボードのみ可能

| キー | 動作 |
|:-----------|:------------|
| 矢印上（↑）/ w（ダブリュー）  |  上に移動      |
| 矢印左（←）/ a（エー）        |     左に移動   |
| 矢印下（↓）/ s（エス）        |      下に移動  |
| 矢印右（→）/ d（ディー）      | 右に移動       |
| スペース                      | 自機の弾の発射 |
| r（アール）                   |       レーダーの表示 |
| j（ジェー）                   |     自爆（ゲームオーバーに遷移） |
| c（シー）                     |     当たり判定の表示  （デバッグ用，プログラム内で有効無効を指定） |

* ステージの作成
マウス操作のみ可能

| マウス     |	場所        |	動作        |
|:-----------|:------------|:------------|
| 左クリック	| マップ上（画面左側）            |	キャラクターの設置                        |
|    〃      |	マップの端（画面左側の端1マス分）|	マップスクロール 長押しで多めにスクロール |
|	    〃     | キャラクターのアイコン           |	設置するキャラクター選択                  |
|	   〃      | タブ	                          | タブの切り替え                             |
|	    〃     | 各種ボタン                      |	ボタン名にある動作                        |
| 右クリック |	マップ上（画面左側）	          | 設置したキャラクターの削除                 |

* その他
マウス，キーボード操作ともに可能

| 操作 |	場所 |	動作 |
|:-----------|:------------|:------------|
| 矢印上（↑）/ w（ダブリュー）	| メニューのある画面	| メニューのスクロール | 
| 矢印下（↓）/ s（エス）      	| 	  〃              |         〃           | 
| 左クリック	                  | ステージ選択画面の矢印アイコン	|    〃     |                  
| スペース                     | メニューアイコン	   |        メニュー選択 | 
| 左クリック	                  |      〃             |         〃           |  
| t(ティー)	                  | モード選択，ステージ選択  | タイトルに戻る | 

## 各クラスの説明
各クラスは以下のようなパッケージ構造となっている

  |__ src  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ manager（ゲーム全体を管理する，各パッケージ間の連携やモードの遷移等）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ method（プログラム全体の固定値やファイルのI/Oを管理）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ objOnMap（ゲーム画面に表示するキャラクターの管理）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ body（自機と敵）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ jiki（自機）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ enemy（敵）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ bullet（弾）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ panel（ゲーム画面の各モードの表示や処理）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ gameClear（ゲームクリア画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ gameOver（ゲームオーバー画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ makerGUI（ステージ作成画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ modeSelect（ゲームモード選択画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ playGame（ゲームプレイ中の画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ stageSelect（ステージ選択画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ titke（タイトル画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ shootingMaker（プログラムのメイン）  
  |   
  |__ data  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ stages（ここ以下にプリセットのステージデータ）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ images（（ここ以下に画像データ）  
  |__ exstages(ここ以下に作成したステージが保存される)    

## キャラクターの追加（プログラムに加筆必要）


## 実装されているキャラクター

