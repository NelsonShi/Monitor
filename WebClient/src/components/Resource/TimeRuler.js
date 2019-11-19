import { Component } from "react";

import styles from './TimeRuler.css'

class TimeRuler extends Component {
    render(){
        return(<div className={styles.rulerWrapper}>
            <ul>
                <li><span>0</span></li>
                <li><span>1</span></li>
                <li><span>2</span></li>
                <li><span>3</span></li>
                <li><span>4</span></li>
                <li><span>5</span></li>
                <li><span>6</span></li>
                <li><span>7</span></li>
                <li><span>8</span></li>
                <li><span>9</span></li>
                <li><span>10</span></li>
                <li><span>11</span></li>
                <li><span>12</span></li>
                <li><span>13</span></li>
                <li><span>14</span></li>
                <li><span>15</span></li>
                <li><span>16</span></li>
                <li><span>17</span></li>
                <li><span>18</span></li>
                <li><span>19</span></li>
                <li><span>20</span></li>
                <li><span>21</span></li>
                <li><span>22</span></li>
                <li><span>23</span></li>
            </ul>
        </div>)
    }
}

export default TimeRuler;