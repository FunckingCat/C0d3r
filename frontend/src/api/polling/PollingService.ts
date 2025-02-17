import { onMounted, onUnmounted, computed } from 'vue';

type ActionFunction<T> = () => Promise<T>
type PollingCallback<T> = (data: T) => void

export class PollingService<T> {
  private intervalId: number | null = null

  constructor(
    public readonly config: {
      name: string
      interval: number
      action: ActionFunction<T>,
      callback: PollingCallback<T>
    }
  ) {}

  async start(): Promise<void> {
    this.stop() 

    console.log(`${this.config.name} polling started`)

    const executePoll = async () => {
      try {
        const response = await this.config.action()
        console.log(`${this.config.name} poll`, response)
        this.config.callback(response)
      } catch (error) {
        console.log(`Error while polling ${this.config.name}`, error)
      }
    }

    await executePoll()
    
    this.intervalId = window.setInterval(
      executePoll,
      this.config.interval
    )
  }

  stop(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId)
      this.intervalId = null
    }
    console.log(`Polling ${this.config.name} stopped`)
  }
  
}