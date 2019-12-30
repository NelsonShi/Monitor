import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer
} from "recharts";
import styles from "./ProcessErrorChart.less";

function Sales({ data }) {
  return (
    <div className={styles.sales}>
      <div className={styles.title}>
        {data.processName}-- Hourly
      </div>
      <ResponsiveContainer minHeight={250}>
        <LineChart data={data.errorChart}>
          <Legend
            verticalAlign="top"
            content={prop => {
              const { payload } = prop;
              return (
                <ul
                  className={classnames({
                    [styles.legend]: true,
                    clearfix: true
                  })}
                >
                  {payload.map((item, key) => (
                    <li key={key}>
                      <span
                        className={styles.radiusdot}
                        style={{ background: item.color }}
                      />
                      {item.value}
                    </li>
                  ))}
                </ul>
              );
            }}
          />
          <XAxis
            dataKey="hour"
            axisLine={{ stroke: "#e5e5e5", strokeWidth: 1 }}
            tickLine={false}
          />
          <YAxis axisLine={false} tickLine={false} />
          <CartesianGrid
            vertical={false}
            stroke="#e5e5e5"
            strokeDasharray="3 3"
          />
          <Tooltip
            wrapperStyle={{
              border: "none",
              boxShadow: "4px 4px 40px rgba(0, 0, 0, 0.05)"
            }}
            content={content => {
              if(content.payload==null)return;
              const list = content.payload.map((item, key) => (
                <li key={key} className={styles.tipitem}>
                  <span
                    className={styles.radiusdot}
                    style={{ background: item.color }}
                  />
                  {`${item.name}:${item.value}`}
                </li>
              ));
              return (
                <div className={styles.tooltip}>
                  <p className={styles.tiptitle}>{content.label}</p>
                  <ul>{list}</ul>
                </div>
              );
            }}
          />
          <Line
            type="monotone"
            dataKey="errorCount"
            stroke="#f8c82e"
            strokeWidth={3}
            dot={{ fill: "#f8c82e" }}
            activeDot={{ r: 5, strokeWidth: 0 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

Sales.propTypes = {
  data: PropTypes.array
};

export default Sales;
